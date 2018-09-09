package cdreyfus.xebia_henri_potier

import android.app.Application
import android.os.Build
import cdreyfus.xebia_henri_potier.basket.promotion.*
import cdreyfus.xebia_henri_potier.book.BookApi
import cdreyfus.xebia_henri_potier.logs.FileLoggingTree
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit


class HenriPotierApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        if (!isRoboUnitTest()) {
            Stetho.initializeWithDefaults(this)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.plant(FileLoggingTree(applicationContext))
    }

    private fun isRoboUnitTest(): Boolean {
        return "robolectric" == Build.FINGERPRINT
    }
    companion object {

        fun createBookApi(): BookApi {
            return setRetrofit().create(BookApi::class.java)
        }

        fun createCommercialOffferApi(): CommercialOfferApi {
            return setRetrofit().create(CommercialOfferApi::class.java)
        }

        private fun setRetrofit(): Retrofit {
            val gson = GsonBuilder()
                    .registerTypeAdapter(CommercialOffer::class.java, CommercialOfferDeserializer())
                    .create()

            return Retrofit.Builder()
                    .baseUrl("http://henri-potier.xebia.fr/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(setHttpClient())
                    .build()
        }


        fun setHttpClient(): OkHttpClient {

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
                    .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))
            return okHttpClient.build()
        }
    }


}
