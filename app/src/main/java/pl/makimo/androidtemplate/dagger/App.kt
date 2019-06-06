package pl.makimo.androidtemplate.dagger

import android.app.Application


class App : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .androidModule(AndroidModule(this))
            .build()
    }
}