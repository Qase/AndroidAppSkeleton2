package wtf.qase.appskeleton.template

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleUnitTest {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    /**
     * Clicks on Add button, excepts
     */
    @Test
    fun clickingOnAddAndReset_shouldIncrementAndResetCounter() {
        // Initial state
        onView(withId(R.id.message)).check(matches(withText("0")))

        // After increasing
        onView(withId(R.id.add)).perform(click())
        onView(withId(R.id.message)).check(matches(withText("1")))

        // After reset
        onView(withId(R.id.reset)).perform(click())
        onView(withId(R.id.message)).check(matches(withText("0")))
    }
}
