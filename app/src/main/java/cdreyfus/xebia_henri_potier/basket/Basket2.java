package cdreyfus.xebia_henri_potier.basket;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cdreyfus.xebia_henri_potier.BuildConfig;
import cdreyfus.xebia_henri_potier.interfaces.BookInterface;
import cdreyfus.xebia_henri_potier.models.Book;
import cdreyfus.xebia_henri_potier.models.CommercialOffer;
import cdreyfus.xebia_henri_potier.models.CommercialOffersResponse;
import cdreyfus.xebia_henri_potier.models.deserializer.BookDeserializer;
import cdreyfus.xebia_henri_potier.models.deserializer.CommercialOfferDeserializer;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class Basket2 {

    private static Basket2 mInstance;

    private LinkedHashMap<Book, Integer> booksQuantitiesMap;

    public static Basket2 getInstance() {
        if (mInstance == null) {
            mInstance = new Basket2();
        }
        return mInstance;
    }

    public Basket2() {
        booksQuantitiesMap = new LinkedHashMap<>();
    }

    public float getRegularPrice() {
        float regularPrice = 0;
        for (Map.Entry<Book, Integer> entry : booksQuantitiesMap.entrySet()) {
            regularPrice += entry.getKey().getPrice() * entry.getValue();
        }
        return regularPrice;
    }

    public LinkedHashMap<Book, Integer> getBooksQuantitiesMap() {
        return booksQuantitiesMap;
    }

    public void addBookToBasket(Book book) {
        booksQuantitiesMap.put(book, 1);
    }

    public void deleteBookFromBasket(Book book) {
        booksQuantitiesMap.remove(book);
    }

    public void editQuantityBook(Book book, int quantity) {
        for (Map.Entry<Book, Integer> entry : booksQuantitiesMap.entrySet()) {
            if (entry.getKey().equals(book)) {
                entry.setValue(quantity);
            }
        }
    }

    public float getFinalPrice() {
        float regularPrice = getRegularPrice();
        return regularPrice - applyBestCommercialOffer(getCommercialOffersResponse(), regularPrice);
    }

    public float getPromoValue(){
        return getFinalPrice() - getRegularPrice();
    }


    private float applyBestCommercialOffer(CommercialOffersResponse commercialOffersResponse, float regularPrice) {
        float minimumValue = regularPrice;
        for (CommercialOffer commercialOffer : commercialOffersResponse.getCommercialOffers()) {
            minimumValue = Math.min(minimumValue, commercialOffer.applyOffer(regularPrice));
        }
        return minimumValue;
    }

    private String getPromotionCode() {
        StringBuilder promotionCode = new StringBuilder();
        for (Map.Entry<Book, Integer> entry : booksQuantitiesMap.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                promotionCode.append(String.format("%s,", entry.getKey().getIsbn()));
            }
        }
        promotionCode = promotionCode.deleteCharAt(promotionCode.length() - 1);

        return promotionCode.toString();
    }

    private CommercialOffersResponse getCommercialOffersResponse(){
        BookInterface bookInterface = setRetrofit().create(BookInterface.class);
        Observable<CommercialOffersResponse> observableCommercialOffer = bookInterface.getCommercialOffer(getPromotionCode());
        return observableCommercialOffer.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .blockingFirst();
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
                .registerTypeAdapter(CommercialOffer.class, new CommercialOfferDeserializer())
                .create();

        return new Retrofit.Builder()
                .baseUrl("http://henri-potier.xebia.fr/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
