package pl.makimo.androidtemplate.dagger

import dagger.Component
import pl.makimo.androidtemplate.BaseActivity
import pl.makimo.androidtemplate.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidModule::class])
interface AppComponent {
    fun inject(target: BaseActivity)
    fun inject(target: MainActivity)
}