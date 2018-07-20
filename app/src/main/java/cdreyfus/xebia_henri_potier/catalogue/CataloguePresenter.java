package cdreyfus.xebia_henri_potier.catalogue;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import cdreyfus.xebia_henri_potier.BuildConfig;
import cdreyfus.xebia_henri_potier.HenriPotierApplication;
import cdreyfus.xebia_henri_potier.book.Book;
import cdreyfus.xebia_henri_potier.book.BookDao;
import cdreyfus.xebia_henri_potier.book.BookDeserializer;
import cdreyfus.xebia_henri_potier.book.IBookInterface;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class CataloguePresenter {

    private View view;
    private Context context;
    private Book book;

    public CataloguePresenter(View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @SuppressLint("CheckResult")
    public void getCatalogue() {

        BookDao bookDao = ((HenriPotierApplication) context).getDaoSession().getBookDao();
        bookDao.loadAll();

        if (isOnline()) {

            IBookInterface IBookInterface = setRetrofit().create(IBookInterface.class);
            Observable<List<Book>> bookObservable = IBookInterface.getBooks();

            bookObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map((Function<List<Book>, Object>) (List<Book> books) -> books)
                    .subscribe(o -> {
                        bookDao.insertOrReplaceInTx((Iterable<Book>) o);
                    }, Timber::d);
        } else {
            view.notConnected();
        }
    }

    public void setView() {
        BookDao bookDao = ((HenriPotierApplication) context).getDaoSession().getBookDao();
        bookDao.loadAll();

        if (!bookDao.queryBuilder().list().isEmpty()) {
            view.showCatalogue(bookDao.queryBuilder().list());
        } else {
            view.showEmpty();
        }
    }

    private Retrofit setRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message)).setLevel(HttpLoggingInterceptor.Level.BODY);

        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addNetworkInterceptor(new StethoInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Book.class, new BookDeserializer())
                .create();

        return new Retrofit.Builder()
                .baseUrl("http://henri-potier.xebia.fr/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = Objects.requireNonNull(cm).getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void clickOnBook(String isbn) {
        view.onBookSelected(isbn);
    }

    public interface View {

        void showCatalogue(List<Book> bookList);

        void showEmpty();

        void onBookSelected(String isbn);

        void notConnected();

    }
}
