package pl.makimo.androidtemplate.retrofit

import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.makimo.androidtemplate.BuildConfig
import pl.makimo.androidtemplate.BuildConfig.DEBUG
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import kotlin.reflect.KClass

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
        fun get() = get(
            "${BuildConfig.API_PROTOCOL}://${BuildConfig.API_URL}",
            Api::class,
            BuildConfig.HTTP_READ_TIMEOUT,
            BuildConfig.HTTP_CONNECT_TIMEOUT
        )

        /**
         * Generate Retrofit client.
         * Parametrized version is to be used in tests.
         */
        fun <T : Any> get(baseUrl: String, apiKClass: KClass<T>,
                          readTimeout: Long, connectTimeout: Long): T {

            val client = OkHttpClient.Builder()
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)

            // Allows to spy on the Network communication via Logcat.
            if (DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                client.addInterceptor(interceptor)
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
