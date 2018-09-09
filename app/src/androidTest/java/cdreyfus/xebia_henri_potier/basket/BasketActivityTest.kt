package cdreyfus.xebia_henri_potier.basket

import android.os.SystemClock
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import cdreyfus.xebia_henri_potier.*
import cdreyfus.xebia_henri_potier.catalogue.CatalogueActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class BasketActivityTest {

    private var title1 = "Henri Potier à l'école des sorciers"
    private var title2 = "Henri Potier et la Chambre des secrets"

    @get:Rule
    var mActivityRule = ActivityTestRule(CatalogueActivity::class.java)


    @Test
    fun basket_is_empty() {
        onView(withId(R.id.action_basket)).perform(click())
        onView(withText("Basket is empty !")).check(matches(isDisplayed()))
    }

    @Test
    fun add_book_to_basket() {
        onView(withText("Henri Potier à l'école des sorciers")).perform(click())
        onView(withText(mActivityRule.activity.resources.getString(R.string.add_to_basket))).perform(click())
        onView(withId(R.id.action_basket)).perform(click())

        onView(withText("Total: 35.00 €")).check(matches(isDisplayed()))
        onView(withText("Henri Potier à l'école des sorciers")).check(matches(isDisplayed()))
        onView(withId(R.id.activity_basket_promo)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_basket_final_price)).check(matches(isDisplayed()))
    }

    @Test
    fun add_book_with_quantity_to_basket() {

        add_books_helper(hashMapOf(title1 to 2))

        onView(withText("Henri Potier à l'école des sorciers")).check(matches(isDisplayed()))
        onView(withId(R.id.basket_item_quantity)).check(matches(withText("x 2")))
        onView(withText("Total: 70.00 €")).check(matches(isDisplayed()))
    }

    @Test
    fun add_two_books_with_quantity_to_basket() {
        add_books_helper(hashMapOf(title1 to 2, title2 to 1))

        onView(withRecyclerView(R.id.activity_basket_list)
                .atPositionOnView(1, R.id.basket_item_label))
                .check(matches(withText(title1)))

        onView(withRecyclerView(R.id.activity_basket_list)
                .atPositionOnView(1, R.id.basket_item_quantity))
                .check(matches(withText("x 2")))


        onView(withRecyclerView(R.id.activity_basket_list)
                .atPositionOnView(0, R.id.basket_item_label))
                .check(matches(withText(title2)))

        onView(withRecyclerView(R.id.activity_basket_list)
                .atPositionOnView(0, R.id.basket_item_quantity))
                .check(matches(withText("x 1")))


        onView(withText("Total: 100.00 €")).check(matches(isDisplayed()))
    }

    @Test
    fun change_quantity_book_shouldShowNumberPicker(){
        add_books_helper(hashMapOf(title1 to 1))
        onView(withRecyclerView(R.id.activity_basket_list).atPosition(0)).perform(click())

        onView(withId(R.id.numberPicker)).check(matches(isDisplayed()))
    }

    @Test
    fun change_quantity_book_with_number_picker_shouldChangeQuantityAndPrice(){
        add_books_helper(hashMapOf(title1 to 1))
        onView(withRecyclerView(R.id.activity_basket_list).atPosition(0)).perform(click())

        onView(ViewMatchers.withId(R.id.numberPicker)).perform(selectNumberPickerValue(2))
        onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())

        onView(withRecyclerView(R.id.activity_basket_list)
                .atPositionOnView(0, R.id.basket_item_quantity))
                .check(matches(withText("x 2")))

        onView(withText("Total: 70.00 €")).check(matches(isDisplayed()))
    }

    @Test
    fun change_quantity_in_basket_navigationInAllActivities_shouldDisplayCorrectBasket(){
        add_books_helper(hashMapOf(title1 to 2, title2 to 1))

        //book2
        onView(withRecyclerView(R.id.activity_basket_list).atPosition(0)).perform(click())
        onView(ViewMatchers.withId(R.id.numberPicker)).perform(selectNumberPickerValue(3))
        onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())

        //book1
        onView(withRecyclerView(R.id.activity_basket_list).atPosition(1)).perform(click())
        onView(ViewMatchers.withId(R.id.numberPicker)).perform(selectNumberPickerValue(5))
        onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())

        Espresso.pressBack()

        onView(withText("Henri Potier et la Coupe de feu")).perform(click())
        onView(withText(mActivityRule.activity.resources.getString(R.string.add_to_basket))).perform(click())

        onView(withId(R.id.action_basket)).perform(click())


        onView(withRecyclerView(R.id.activity_basket_list)
                .atPositionOnView(0, R.id.basket_item_quantity))
                .check(matches(withText("x 1")))

        onView(withRecyclerView(R.id.activity_basket_list)
                .atPositionOnView(1, R.id.basket_item_quantity))
                .check(matches(withText("x 3")))

        onView(withRecyclerView(R.id.activity_basket_list)
                .atPositionOnView(2, R.id.basket_item_quantity))
                .check(matches(withText("x 5")))


    }

    private fun add_books_helper(books: HashMap<String, Int>) {

        for (bookEntry in books) {
            onView(withText(bookEntry.key)).perform(click())
            setBookInBasket_fromBookActivity(bookEntry.value)
            Espresso.pressBack()
        }
        onView(withId(R.id.action_basket)).perform(click())
    }
}