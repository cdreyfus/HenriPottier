package cdreyfus.xebia_henri_potier

import android.app.Application
import cdreyfus.xebia_henri_potier.basket.promotion.CommercialOffer
import cdreyfus.xebia_henri_potier.basket.promotion.CommercialOfferDeserializer
import cdreyfus.xebia_henri_potier.basket.promotion.ICommercialOfferApi
import cdreyfus.xebia_henri_potier.book.Book
import cdreyfus.xebia_henri_potier.book.BookDeserializer
import cdreyfus.xebia_henri_potier.book.IBookApi
import cdreyfus.xebia_henri_potier.logs.FileLoggingTree
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

class HenriPotierApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.plant(FileLoggingTree(applicationContext))
    }


    companion object {
        val CATALOGUE: String = "CATALOGUE_HENRI_POTIER"

        fun createBookApi(): IBookApi {
            return setRetrofit().create(IBookApi::class.java)
        }

        fun createCommercialOffferApi(): ICommercialOfferApi {
            return setRetrofit().create(ICommercialOfferApi::class.java)
        }

        fun setRetrofit(): Retrofit {
            val logging = HttpLoggingInterceptor { message -> Timber.tag("OkHttp").d(message) }.setLevel(HttpLoggingInterceptor.Level.BODY)

            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.BASIC
            }

            val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addNetworkInterceptor(StethoInterceptor())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .connectionSpecs(Arrays.asList(
                            ConnectionSpec.MODERN_TLS,
                            ConnectionSpec.COMPATIBLE_TLS))
                    .build()

            val gson = GsonBuilder()
                    .registerTypeAdapter(CommercialOffer::class.java, CommercialOfferDeserializer())
                    .registerTypeAdapter(Book::class.java, BookDeserializer())
                    .create()

            return Retrofit.Builder()
                    .baseUrl("http://henri-potier.xebia.fr/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()
        }

    }


}
