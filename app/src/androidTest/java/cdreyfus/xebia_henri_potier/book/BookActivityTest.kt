package cdreyfus.xebia_henri_potier.book

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import cdreyfus.xebia_henri_potier.R
import cdreyfus.xebia_henri_potier.catalogue.CatalogueActivity
import org.hamcrest.core.IsNot.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class BookActivityTest {

    @Rule
    var mActivityRule = ActivityTestRule(CatalogueActivity::class.java)

    @Before
    fun clickHenriPotierEcoleSorciers() {
        onView(withText("Henri Potier à l'école des sorciers")).perform(click())
    }

    @Test
    fun setBookInBasket() {
        onView(withId(R.id.activity_book_add_to_basket)).perform(click())

        onView(withText("Edit quantity (1)")).check(matches(isDisplayed()))
        onView(withId(R.id.activity_book_remove_from_basket_button)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_book_edit_quantity_button)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_book_add_to_basket)).check(matches(not<View>(isDisplayed())))
    }

    @Test
    fun setNotInBasket() {
        onView(withId(R.id.activity_book_remove_from_basket_button)).check(matches(not<View>(isDisplayed())))
        onView(withId(R.id.activity_book_edit_quantity_button)).check(matches(not<View>(isDisplayed())))
        onView(withId(R.id.activity_book_add_to_basket)).check(matches(isDisplayed()))
    }

    @Test
    fun showNumberPicker() {
        onView(withId(R.id.activity_book_add_to_basket)).perform(click())
        onView(withId(R.id.activity_book_edit_quantity_button)).perform(click())

        onView(withId(R.id.numberPicker)).check(matches(isDisplayed()))
        onView(withText("1")).check(matches(isDisplayed()))
    }

    @Test
    fun hideNumberPicker_OK() {
        onView(withId(R.id.activity_book_add_to_basket)).perform(click())
        onView(withId(R.id.activity_book_edit_quantity_button)).perform(click())

        onView(withId(R.id.numberPicker)).check(matches(isDisplayed()))
        onView(withId(android.R.id.button1)).perform(click())

        onView(withId(R.id.numberPicker)).check(doesNotExist())
    }

    @Test
    fun hideNumberPicker_Cancel() {
        onView(withId(R.id.activity_book_add_to_basket)).perform(click())
        onView(withId(R.id.activity_book_edit_quantity_button)).perform(click())

        onView(withId(android.R.id.button2)).perform(click())
        onView(withId(R.id.numberPicker)).check(doesNotExist())
    }


    @Test
    fun clickRemoveFromBasket() {
        onView(withId(R.id.activity_book_add_to_basket)).perform(click())

        onView(withId(R.id.activity_book_remove_from_basket_button)).perform(click())
        onView(withId(R.id.activity_book_add_to_basket)).check(matches(isDisplayed()))

    }

    @Test
    fun clickEditQuantity_change() {
        onView(withId(R.id.activity_book_add_to_basket)).perform(click())
        onView(withId(R.id.activity_book_edit_quantity_button)).perform(click())

        //        onData(is(instanceOf(String.class))).inAdapterView(allOf(withClassName(equalTo("com.cuiweiyou.numberpickerdialog.NumberPickerDialog")), isDisplayed())).atPosition(index).perform(click());
        //        onView(withId(R.id.numberPicker)).inRoot(withClassName(equalTo("com.cuiweiyou.numberpickerdialog.NumberPickerDialog"))).
        //        onView(withId(R.id.numberPicker)).perform(new ViewAction() {
        //            @Override
        //            public Matcher<View> getConstraints() {
        //                return ViewMatchers.isAssignableFrom(NumberPickerDialog.class);
        //            }
        //
        //            @Override
        //            public String getDescription() {
        //                return "Set the value of a NumberPicker";
        //            }
        //
        //            @Override
        //            public void perform(UiController uiController, View view) {
        //                ((NumberPickerDialog)view).setCurrentValue(3);
        //            }
        //        });

        onView(withId(android.R.id.button1)).perform(click())
        onView(withText("Edit quantity (3)")).check(matches(isDisplayed()))

    }

}