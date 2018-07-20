package cdreyfus.xebia_henri_potier.basket;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import butterknife.BindView;
import cdreyfus.xebia_henri_potier.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BasketActivityTest {

//    @BindView(R.id.activity_basket_final_price)
//    TextView mFinalPrice;
//    @BindView(R.id.activity_basket_regular_price)
//    TextView mRegularPrice;
//    @BindView(R.id.activity_basket_promo)
//    TextView mPromo;
//    @BindView(R.id.activity_basket_list)
//    RecyclerView recyclerView;
//    @BindView(R.id.activity_basket_empty)
//    TextView mEmptyBasket;

    @Rule
    public ActivityTestRule<BasketActivity> mActivityRule = new ActivityTestRule<>(BasketActivity.class);


//    @Before
//    public void initRegularPrice(){
//        mActivityRule.launchActivity(new Intent())
//    }

    @Test
    public void setRegularPrice() {
        mActivityRule.getActivity().setRegularPrice(12);
        onView(withId(R.id.activity_basket_regular_price)).check(matches(withText("Total: 12.00 €")));
    }

    @Test
    public void setFinalPrice() {
        mActivityRule.getActivity().setFinalPrice(6.50f);
        onView(withId(R.id.activity_basket_final_price)).check(matches(withText("New Total: 6.50 €")));

    }

    @Test
    public void setPromoValue() {
        mActivityRule.getActivity().setPromoValue(5.50f);
        onView(withId(R.id.activity_basket_final_price)).check(matches(withText("Promotion: 5.50 €")));

    }

//    @Test
//    public void showBooks() {
//    }
//
//    @Test
//    public void showEmpty() {
//    }
}