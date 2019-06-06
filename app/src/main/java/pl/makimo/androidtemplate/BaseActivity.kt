package pl.makimo.androidtemplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.makimo.androidtemplate.dagger.App

/**
 * Basis for all [Activities]. All [Activities] must inherit from this one.
 */
open class BaseActivity : AppCompatActivity() {
    /**
     * Shortcut for Dagger initialization. One can simply do `component().inject(this)`.
     * "component" here stands for [DaggerAppComponent] which is a Dagger-generated
     * class injecting the dependencies.
     */
    val component by lazy { (application as App).component }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }
}
