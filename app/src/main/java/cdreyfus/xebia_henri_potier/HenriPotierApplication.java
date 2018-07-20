package cdreyfus.xebia_henri_potier;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.greendao.database.Database;

import java.util.concurrent.TimeUnit;

import cdreyfus.xebia_henri_potier.basket.promotion.CommercialOffer;
import cdreyfus.xebia_henri_potier.book.Book;
import cdreyfus.xebia_henri_potier.book.BookDeserializer;
import cdreyfus.xebia_henri_potier.book.DaoMaster;
import cdreyfus.xebia_henri_potier.book.DaoSession;
import cdreyfus.xebia_henri_potier.logs.FileLoggingTree;
import cdreyfus.xebia_henri_potier.basket.promotion.CommercialOfferDeserializer;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class HenriPotierApplication extends Application{

    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Timber.plant(new FileLoggingTree(getApplicationContext()));

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "xebia_henri_potier-db");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

}
