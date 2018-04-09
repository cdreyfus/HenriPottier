package cdreyfus.xebia_henri_potier.activity;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CatalogueActivityTest {

    @Rule
    public ActivityTestRule<CatalogueActivity> mActivityRule = new ActivityTestRule<>(CatalogueActivity.class);

//    @Test
//    public void showCatalogueBooks() {
//        onView(withId(R.id.activity_catalogue_list_view)).check(matches(isDisplayed()));
//    }

//    @Test
//    public void list_complete(){
//        onRow()
//    }
}