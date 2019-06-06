package pl.makimo.androidtemplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.makimo.androidtemplate.dagger.App

/**
 * Basis for all [Activities]. All [Activities] must inherit from this one.
 */
open class BaseActivity : AppCompatActivity() {

    val component by lazy { (application as App).component }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }
}
