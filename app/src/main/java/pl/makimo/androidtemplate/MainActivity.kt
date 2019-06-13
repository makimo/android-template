package pl.makimo.androidtemplate

import android.os.Bundle
import com.amitshekhar.DebugDB
import timber.log.Timber

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)

        //        Implement me!
        //
        //        ,=\.-----""""^==--
        //     ;;'( ,___, ,/~\;
        //     '  )/>/   \|-,
        //        | `\    | "
        //        "   "   "

        Timber.d("Ponycorn!")
        DebugDB.getAddressLog()
    }
}
