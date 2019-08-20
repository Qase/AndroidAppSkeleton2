package wtf.qase.appskeleton.example

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers.isSystemAlertWindow
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import wtf.qase.appskeleton.example.main.todos.TodoAdapter

@RunWith(AndroidJUnit4::class)
class TodoTest {

    companion object {

        // Custom matcher for RecycleView
        fun atPosition(position: Int, matcher: Matcher<View>): Matcher<View> {
            return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
                override fun describeTo(description: Description) {
                    description.appendText("has item at position $position: ");
                    matcher.describeTo(description)
                }

                override fun matchesSafely(view: RecyclerView?): Boolean {
                    return view?.findViewHolderForAdapterPosition(position)?.let {
                        matcher.matches(it.itemView)
                    } ?: false
                }
            }
        }
    }

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("wtf.qase.appskeleton.example", appContext.packageName)
    }

    @Test
    fun todoLifecycle() {
        // Open dialog
        onView(withId(R.id.add)).perform(click())
        onView(withText(R.string.todo_dialog_title)).check(matches(isDisplayed()))

        // Create item
        onView(withId(R.id.title)).perform(replaceText("title"))
        onView(withId(R.id.desc)).perform(replaceText("description"))
        onView(withId(R.id.create)).perform(click())
        onView(withId(R.id.list)).check(matches(atPosition(0, hasDescendant(withText("title")))))

        // Show detail
        onView(withId(R.id.list)).perform(actionOnItemAtPosition<TodoAdapter.ViewHolder>(0, click()))
        onView(withId(R.id.title)).check(matches(withText("title")))
        onView(withId(R.id.desc)).check(matches(withText("description")))

        // Go back
        Espresso.pressBack()

        // Delete item
        onView(withId(R.id.popupMenuButton)).perform(click())
        onView(withText(R.string.todo_menu_delete)).perform(click())
        onView(withText(R.string.deleted)).inRoot(isSystemAlertWindow()).check(matches(isDisplayed()))

        // Ugly sleeping to wait toast disappears
        Thread.sleep(2_000)
    }

    @Test
    fun cancelDialog() {
        onView(withId(R.id.add)).perform(click())
        onView(withId(R.id.create)).perform(click())
        onView(withText("Title is blank")).inRoot(isSystemAlertWindow()).check(matches(isDisplayed()))
        onView(withId(R.id.cancel)).perform(click())

        // Ugly sleeping to wait toast disappears
        Thread.sleep(2_000)
    }
}
