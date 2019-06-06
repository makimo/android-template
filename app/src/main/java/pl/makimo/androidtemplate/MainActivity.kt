package pl.makimo.androidtemplate

import android.os.Bundle

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
    }
}
