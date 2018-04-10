package cdreyfus.xebia_henri_potier.activity;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.RestServiceTestHelper;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class CatalogueActivityTest {
    @Rule
    public ActivityTestRule<CatalogueActivity> mActivityRule =
            new ActivityTestRule<>(CatalogueActivity.class, true, false);
    private MockWebServer server;


    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        server.start();
    }

    @Test
    public void setView() {
    }

    @Test
    public void getValidCatalog() throws Exception {
        String fileName = "catalogue_200.json";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.activity_catalogue_empty_db)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withText("Henri Potier et les Reliques de la Mort")).check(matches(isDisplayed()));
    }
}