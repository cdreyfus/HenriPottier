package cdreyfus.xebia_henri_potier.basket

import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import cdreyfus.xebia_henri_potier.R
import cdreyfus.xebia_henri_potier.catalogue.CatalogueActivity

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText

@RunWith(AndroidJUnit4::class)
@LargeTest
class BasketActivityTest {

    @Rule
    var mActivityRule = ActivityTestRule(CatalogueActivity::class.java)

    @Test
    fun add_book_to_basket() {
        onView(withText("Henri Potier à l'école des sorciers")).perform(click())
        onView(withText(mActivityRule.activity.resources.getString(R.string.add_to_basket))).perform(click())
        onView(withId(R.id.action_basket)).perform(click())
        onView(withText("Total: 35.00 €")).check(matches(isDisplayed())) // Better is to set Total: in strings.xml so reference can be used
        onView(withText("Henri Potier à l'école des sorciers")).check(matches(isDisplayed()))
    }


}