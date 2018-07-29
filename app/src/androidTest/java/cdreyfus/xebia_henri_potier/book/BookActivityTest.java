package cdreyfus.xebia_henri_potier.book;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.cuiweiyou.numberpickerdialog.NumberPickerDialog;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.catalogue.CatalogueActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.JMock1Matchers.equalTo;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BookActivityTest {

    @Rule
    public ActivityTestRule<CatalogueActivity> mActivityRule = new ActivityTestRule<>(CatalogueActivity.class);

    @Before public void clickHenriPotierEcoleSorciers(){
        onView(withText("Henri Potier à l'école des sorciers")).perform(click());
    }

    @Test
    public void setBookInBasket() {
        onView(withId(R.id.activity_book_add_to_basket)).perform(click());

        onView(withText("Edit quantity (1)")).check(matches(isDisplayed()));
        onView(withId(R.id.activity_book_remove_from_basket_button)).check(matches(isDisplayed()));
        onView(withId(R.id.activity_book_edit_quantity_button)).check(matches(isDisplayed()));
        onView(withId(R.id.activity_book_add_to_basket)).check(matches(not(isDisplayed())));
    }

    @Test
    public void setNotInBasket() {
        onView(withId(R.id.activity_book_remove_from_basket_button)).check(matches(not(isDisplayed())));
        onView(withId(R.id.activity_book_edit_quantity_button)).check(matches(not(isDisplayed())));
        onView(withId(R.id.activity_book_add_to_basket)).check(matches(isDisplayed()));
    }

    @Test
    public void showNumberPicker() {
        onView(withId(R.id.activity_book_add_to_basket)).perform(click());
        onView(withId(R.id.activity_book_edit_quantity_button)).perform(click());

        onView(withId(R.id.numberPicker)).check(matches(isDisplayed()));
        onView(withText("1")).check(matches(isDisplayed()));
    }

    @Test
    public void hideNumberPicker_OK() {
        onView(withId(R.id.activity_book_add_to_basket)).perform(click());
        onView(withId(R.id.activity_book_edit_quantity_button)).perform(click());

        onView(withId(R.id.numberPicker)).check(matches(isDisplayed()));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.numberPicker)).check(doesNotExist());
    }

    @Test
    public void hideNumberPicker_Cancel() {
        onView(withId(R.id.activity_book_add_to_basket)).perform(click());
        onView(withId(R.id.activity_book_edit_quantity_button)).perform(click());

        onView(withId(android.R.id.button2)).perform(click());
        onView(withId(R.id.numberPicker)).check(doesNotExist());
    }


    @Test
    public void clickRemoveFromBasket() {
        onView(withId(R.id.activity_book_add_to_basket)).perform(click());

        onView(withId(R.id.activity_book_remove_from_basket_button)).perform(click());
        onView(withId(R.id.activity_book_add_to_basket)).check(matches(isDisplayed()));

    }

    @Test
    public void clickEditQuantity_change() {
        onView(withId(R.id.activity_book_add_to_basket)).perform(click());
        onView(withId(R.id.activity_book_edit_quantity_button)).perform(click());

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

        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("Edit quantity (3)")).check(matches(isDisplayed()));

    }

}