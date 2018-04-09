package cdreyfus.xebia_henri_potier.activity;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cdreyfus.xebia_henri_potier.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class BookActivityTest {

    @Rule
    public ActivityTestRule<BookActivity> mActivityRule = new ActivityTestRule<>(BookActivity.class);

//    @Test
//    public void showBookDetail() {
//        onView(withId(R.id.activity_book_cover)).check(matches(isDisplayed()));
//        onView(withId(R.id.activity_book_price)).check(matches(isDisplayed()));
//        onView(withId(R.id.activity_book_synopsis)).check(matches(isDisplayed()));
//    }
}

