package pl.makimo.androidtemplate.retrofit

import android.content.Context
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.makimo.androidtemplate.BuildConfig
import pl.makimo.androidtemplate.BuildConfig.DEBUG
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import kotlin.reflect.KClass
import com.readystatesoftware.chuck.ChuckInterceptor



/**
 * `Api` object builder.
 * Example usage:
 *
 *     ApiService.get().login(...)
 */
class ApiService {

    companion object {
        /**
         * Generate Retrofit client.
         * No-argument version is to be used by the application.
         */
        fun get(context: Context) = get(
            context,
            "${BuildConfig.API_PROTOCOL}://${BuildConfig.API_URL}",
            Api::class,
            BuildConfig.HTTP_READ_TIMEOUT,
            BuildConfig.HTTP_CONNECT_TIMEOUT
        )

        /**
         * Generate Retrofit client.
         * Parametrized version is to be used in tests.
         */
        fun <T : Any> get(context: Context, baseUrl: String, apiKClass: KClass<T>,
                          readTimeout: Long, connectTimeout: Long): T {

            val client = OkHttpClient.Builder()
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)

            if (DEBUG) {
                // Allows to spy on the Network communication via Logcat.
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                client.addInterceptor(interceptor)

                // Allows to spy on the Network communication via notifications.
                client.addInterceptor(ChuckInterceptor(context))
            }

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client.build())
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(apiKClass.java)
        }
    }
}
