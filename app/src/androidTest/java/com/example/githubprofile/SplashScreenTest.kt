package com.example.githubprofile

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.githubprofile.ui.SplashScreenAcitivity
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SplashScreenTest {
    val dummyText = "Github Profile Search"
    @Before
    fun setup(){
        ActivityScenario.launch(SplashScreenAcitivity::class.java)
    }
    @Test
    fun checkSplash() {
        Espresso.onView(withId(R.id.splash_image)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.splash_text)).check(matches(isDisplayed())).check(matches(
            withText(dummyText)
        ))
    }
}