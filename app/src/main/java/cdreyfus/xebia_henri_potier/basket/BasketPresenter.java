package cdreyfus.xebia_henri_potier.basket;

import android.annotation.SuppressLint;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import cdreyfus.xebia_henri_potier.BuildConfig;
import cdreyfus.xebia_henri_potier.basket.promotion.CommercialOffer;
import cdreyfus.xebia_henri_potier.basket.promotion.CommercialOffersResponse;
import cdreyfus.xebia_henri_potier.basket.promotion.ICommercialOfferApi;
import cdreyfus.xebia_henri_potier.models.Book;
import cdreyfus.xebia_henri_potier.models.deserializer.CommercialOfferDeserializer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class BasketPresenter {

    private Basket basket;
    private View view;

    public BasketPresenter(View view) {
        this.view = view;
        basket = Basket.getInstance();
    }

    @SuppressLint("CheckResult")
    public void setPrices() {

        float regularPrice = basket.getRegularPrice();
        view.setRegularPrice(regularPrice);

        if (!basket.isEmpty()) {
            ICommercialOfferApi commercialOfferInterface = setRetrofit().create(ICommercialOfferApi.class);

            Single<CommercialOffersResponse> singleCommercialOffer = commercialOfferInterface.getCommercialOffer(basket.getPromotionCode());

            singleCommercialOffer.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(commercialOffersResponse -> {
                        float finalPrice = basket.applyBestCommercialOffer(commercialOffersResponse, regularPrice);
                        view.setFinalPrice(finalPrice);
                        view.setPromoValue(finalPrice - regularPrice);

                    }, Timber::d);
        } else {
            view.setFinalPrice(regularPrice);
            view.setPromoValue(0);
        }
    }

    public void updateBasketContent() {
        if (basket.isEmpty()) {
            view.showEmpty();
        } else {
            view.showBooks(basket.getBooksQuantitiesMap());
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
                .registerTypeAdapter(CommercialOffer.class, new CommercialOfferDeserializer())
                .create();

        return new Retrofit.Builder()
                .baseUrl("http://henri-potier.xebia.fr/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public interface View {

        void setRegularPrice(float regularPrice);

        void setFinalPrice(float finalPrice);

        void setPromoValue(float promoValue);

        void showBooks(LinkedHashMap<Book, Integer> listBooks);

        void showEmpty();
    }
}
