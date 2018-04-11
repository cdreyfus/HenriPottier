package cdreyfus.xebia_henri_potier.views;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.activity.CatalogueActivity;
import cdreyfus.xebia_henri_potier.models.Book;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CatalogueItemViewTest {

    @Rule
    public MockWebServer server = new MockWebServer();

    @Rule
    public ActivityTestRule<CatalogueActivity> mActivityRule = new ActivityTestRule<>(
            CatalogueActivity.class);

    @Before
    public void setUp() throws Exception {
        Book book = new Book("cocorico",
                "Henri Pottier et l'enfant maudit",
                30,
                "Le 8ème tome",
                "https://c-ash.smule.com/sf/s78/arr/82/e9/0cc53de2-73ec-4386-be8c-3ed644b952fe_256.jpg"
        );
    }

    @Test
    public void viewIsCorrectlySetup(){
        onView(ViewMatchers.withId(R.id.catalogue_item_label)).check(matches(withText("Henri Pottier et l'enfant maudit")))
    }
}