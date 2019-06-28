/**
 * Functional extensions to existing classes.
 * Helpers and utility functions.
 *
 * If this file grows too large, it should be split or
 * a ticket should be issued to "Prefabrykaty".
 */
package pl.makimo.androidtemplate.kotlin

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import io.reactivex.Observable
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import pl.makimo.androidtemplate.BaseActivity
import kotlin.reflect.KClass

//
// BaseActivity
//

/**
 * We want centralized control over how [Activities] are started, hence the shortcut.
 * For example, we want to remove default animation between [Activities], like
 * Instagram.
 */
fun BaseActivity.start(callee: Class<out BaseActivity>) =
    apply {
        startActivity(Intent(this, callee))
        overridePendingTransition(0, 0)
    }

fun BaseActivity.start(intent: Intent) =
    apply {
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

/**
 * Start an activity by specifying a Kotlin class and
 * function to be applied to the Intent.
 * Example usage:
 *
 *     start(OtherActivity::class, {
 *         // Put data into intent.
 *         putExtra(SOME_VAL, someVal)
 *     })
 */
fun <T : BaseActivity> BaseActivity.start(klass: KClass<T>, fn: Intent.() -> Unit) =
    start(intent(klass) {
        fn(this)
    })

fun <T> BaseActivity.find(viewId: Int) = findViewById<View>(viewId) as T

//
// Context
//

fun Context.string(resId: Int) = resources.getString(resId)

fun Context.string(resId: String) =
    getString(resources.getIdentifier(resId, "string", packageName))

fun Context.toast(textRestId: Int) =
    Toast.makeText(this, resources.getString(textRestId), Toast.LENGTH_SHORT).show()

fun Context.toastLong(textRestId: Int) =
    Toast.makeText(this, resources.getString(textRestId), Toast.LENGTH_LONG).show()

fun <T : BaseActivity> Context.intent(klass: KClass<T>, fn: Intent.() -> Unit) =
    Intent(this, klass.java).apply { fn(this) }

//
// Views
//

/**
 * Android does not provide a method to select all
 * views with a given tag. Not sure why...
 */
fun <T> View.findViewsByTag(tag: String?): ArrayList<T> {
    val root = this as ViewGroup
    val views = ArrayList<T>()

    for (i in 0 until root.childCount) {
        val child = root.getChildAt(i)

        if (child is ViewGroup) {
            views.addAll(child.findViewsByTag<T>(tag))
        } else if (child.tag != null && child.tag == tag) {
            views.add(child as T)
        }
    }

    return views
}

//
// ViewPager
//

fun ViewPager.setOnPageSelected(onPageSelectedFun: (Int) -> Unit) {
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(p: Int, p0: Float, p1: Int) {}

        override fun onPageSelected(position: Int) {
            onPageSelectedFun(position)
        }
    })
}

fun ViewPager.setOnPageChangedListener(
    onPageScrollStateChangedFun: (Int) -> Unit,
    onPageScrolledFun: (Int, Float, Int) -> Unit,
    onPageSelectedFun: (Int) -> Unit
) {
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

        override fun onPageScrollStateChanged(state: Int) {
            onPageScrollStateChangedFun(state)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            onPageScrolledFun(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            onPageSelectedFun(position)
        }
    })
}

//
// RxKotlin extension functions
//
// The following functions are used to minimize the boilerplate
// when creating [Observables]. Each type of [View] has typical
// actions which are of interest to us. For example, [View] will
// have "click" and [TextView] "text changed". Each such action,
// should have a dedicated method which avoids needles
// initialization.


fun View.click(block: Observable<Unit>.() -> Unit) {
    val observable: Observable<Unit> = Observable.create { emitter ->
        setOnClickListener {
            emitter.onNext(Unit)
        }

        emitter.setCancellable {
            setOnClickListener(null)
        }
    }

    block(observable)
}

fun View.onClick(block: () -> Unit) {
    setOnClickListener { block() }
}

fun View.find(viewId: Int) = findViewById<View>(viewId)

fun EditText.text(block: Observable<String>.() -> Unit) {
    val observable = Observable.create<String> { emitter ->

        val textWatcher = object : TextWatcher {

            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) = Unit

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                s?.toString()?.let { emitter.onNext(it) }
            }
        }

        addTextChangedListener(textWatcher)

        emitter.setCancellable {
            removeTextChangedListener(textWatcher)
        }
    }

    block(observable)
}

fun EditText.onText(block: (String) -> Unit) {
    val textWatcher = object : TextWatcher {

        override fun afterTextChanged(s: Editable?) = Unit

        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) = Unit

        override fun onTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
            s?.toString()?.let { block(it) }
        }
    }

    addTextChangedListener(textWatcher)
}

fun CheckBox.onChanged(block: (Boolean) -> Unit) {
    setOnCheckedChangeListener { _, isChecked ->
        block(isChecked)
    }
}

fun Spinner.onSelected(fn: (String, Int) -> Unit) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {}

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
            fn(getItemAtPosition(pos).toString(), pos)
        }
    }
}

//
// Primitives and collections
//

fun String.isValidEmail() =
    this.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun <E> MutableCollection<E>.reset(replace: List<E>): MutableCollection<E> {
    clear()
    addAll(replace)
    return this
}

// Kotlin

/**
 * Compose two functions and return the resulting one.
 * Example usage:
 *
 *     val stringToInt:  (String) -> Int  = it.toInt()
 *     val intToLong:    (Int)    -> Long = it.toLong()
 *     val stringToLong: (String) -> Long = compose(intToLong, stringToInt)
 */
fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C = { x -> f(g(x)) }
