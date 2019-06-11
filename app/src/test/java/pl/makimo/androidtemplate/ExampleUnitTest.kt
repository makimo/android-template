package pl.makimo.androidtemplate

import org.junit.Test

import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 */
@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {

    @Test
    fun clickingButton_shouldChangeMessage() {
        assert(Robolectric.setupActivity(MainActivity::class.java) != null)
    }
}
