package pl.makimo.androidtemplate.dagger

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import pl.makimo.androidtemplate.BuildConfig
import pl.makimo.androidtemplate.retrofit.ApiService
import javax.inject.Named
import javax.inject.Singleton

/**
 * Actual provider of injectable elements. Each method
 * annotated with [Provides] takes care of one injectable
 * class.
 */
@Module
class AndroidModule(private val application: Application) {

    companion object {
        private const val BASE_URL = "BASE_URL"
    }

    @Provides
    @Singleton
    internal fun provideApplicationContext() = application as Context

    @Provides
    @Named(BASE_URL)
    fun provideBaseUrlString() = "${BuildConfig.API_PROTOCOL}://${BuildConfig.API_URL}"

    @Provides
    @Singleton
    fun provideApi() = ApiService.get(application)
}