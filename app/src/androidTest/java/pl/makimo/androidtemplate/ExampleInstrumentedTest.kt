package pl.makimo.androidtemplate

import androidx.test.rule.ActivityTestRule

import org.junit.Test
import org.junit.Rule
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.Assert.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import pl.makimo.androidtemplate.retrofit.ApiService

/**
 * Boilerplate test for checking build configuration.
 */
@RunWith(AndroidJUnit4::class)
class BuildConfigTest {
    @Rule
    @JvmField
    val rule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun apiServiceBuilds() {
        try {
            ApiService.get()
            assert(true)
        } catch (e: Throwable) {
            assert(false)
        }
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals(BuildConfig.PACKAGE, appContext.packageName)
    }
}
