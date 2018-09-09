package cdreyfus.xebia_henri_potier.book

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import cdreyfus.xebia_henri_potier.R
import cdreyfus.xebia_henri_potier.catalogue.CatalogueActivity
import cdreyfus.xebia_henri_potier.selectNumberPickerValue
import cdreyfus.xebia_henri_potier.setBookInBasket_fromBookActivity
import cdreyfus.xebia_henri_potier.withRecyclerView
import org.hamcrest.core.IsNot.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class BookActivityTest {

    @get:Rule
    var mActivityRule = ActivityTestRule(CatalogueActivity::class.java)

    @Before
    fun select_book() {
        onView(withText("Henri Potier à l'école des sorciers")).perform(click())
    }

    @Test
    fun set_book_shouldDisplayBook() {
        onView(withText("Henri Potier à l'école des sorciers")).check(matches(isDisplayed()))
        onView(withId(R.id.activity_book_add_to_basket)).check(matches(isDisplayed()))
    }

    @Test
    fun add_book_to_basket_shouldAddOne() {
        onView(withId(R.id.activity_book_add_to_basket)).perform(click())

        onView(withText("Edit quantity (1)")).check(matches(isDisplayed()))
        onView(withId(R.id.activity_book_remove_from_basket_button)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_book_edit_quantity_button)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_book_add_to_basket)).check(matches(not<View>(isDisplayed())))
    }

    @Test
    fun set_book_not_in_basket_shouldDisplayDefaultButttons() {
        onView(withId(R.id.activity_book_remove_from_basket_button)).check(matches(not<View>(isDisplayed())))
        onView(withId(R.id.activity_book_edit_quantity_button)).check(matches(not<View>(isDisplayed())))
        onView(withId(R.id.activity_book_add_to_basket)).check(matches(isDisplayed()))
    }

    @Test
    fun show_number_picker_shouldDisplayDialog() {
        onView(withId(R.id.activity_book_add_to_basket)).perform(click())
        onView(withId(R.id.activity_book_edit_quantity_button)).perform(click())

        onView(withId(R.id.numberPicker)).check(matches(isDisplayed()))
        onView(withText("1")).check(matches(isDisplayed()))
    }

    @Test
    fun hide_number_picker_ClickOnCancel_shouldKeepOneBookInBasket() {
        onView(withId(R.id.activity_book_add_to_basket)).perform(click())
        onView(withId(R.id.activity_book_edit_quantity_button)).perform(click())

        onView(withId(android.R.id.button2)).perform(click())
        onView(withId(R.id.numberPicker)).check(doesNotExist())
        onView(withText("Edit quantity (1)")).check(matches(isDisplayed()))
    }


    @Test
    fun remove_from_basket() {
        onView(withId(R.id.activity_book_add_to_basket)).perform(click())

        onView(withId(R.id.activity_book_remove_from_basket_button)).perform(click())
        onView(withId(R.id.activity_book_add_to_basket)).check(matches(isDisplayed()))

    }

    @Test
    fun edit_quantity_shouldSetNewQuantityForBook() {
        setBookInBasket_fromBookActivity(5)
        onView(withId(R.id.numberPicker)).check(doesNotExist())
        onView(withText("Edit quantity (5)")).check(matches(isDisplayed()))
    }

    @Test
    fun basket_stays_with_same_quantity_OneBook_backAndForthCatalogue() {
        setBookInBasket_fromBookActivity(5)
        Espresso.pressBack()
        onView(withText("Henri Potier à l'école des sorciers")).perform(click())
        onView(withText("Edit quantity (5)")).check(matches(isDisplayed()))
    }

    @Test
    fun basket_stays_with_same_quantity_TwoBooks_backAndForthCatalogue() {
        setBookInBasket_fromBookActivity(5)
        Espresso.pressBack()

        onView(withText("Henri Potier et la Chambre des secrets")).perform(click())
        setBookInBasket_fromBookActivity(2)
        Espresso.pressBack()

        onView(withText("Henri Potier à l'école des sorciers")).perform(click())
        onView(withText("Edit quantity (5)")).check(matches(isDisplayed()))
        Espresso.pressBack()

        onView(withText("Henri Potier et la Chambre des secrets")).perform(click())
        onView(withText("Edit quantity (2)")).check(matches(isDisplayed()))
    }

    @Test
    fun quantity_changed_in_basket_adapted_in_book_view_shouldChangeQuantity(){
        setBookInBasket_fromBookActivity(5)
        onView(withId(R.id.action_basket)).perform(click())

        onView(withRecyclerView(R.id.activity_basket_list).atPosition(0)).perform(click())
        onView(ViewMatchers.withId(R.id.numberPicker)).perform(selectNumberPickerValue(2))
        onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())
        Espresso.pressBack()

        onView(withText("Edit quantity (2)")).check(matches(isDisplayed()))

    }

}