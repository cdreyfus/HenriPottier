package cdreyfus.xebia_henri_potier.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.activity.models.HenriPotierActivity;
import cdreyfus.xebia_henri_potier.adapter.BasketAdapter;
import cdreyfus.xebia_henri_potier.interfaces.BookInterface;
import cdreyfus.xebia_henri_potier.models.Basket;
import cdreyfus.xebia_henri_potier.models.CommercialOffersResponse;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class BasketActivity extends HenriPotierActivity {

    @BindView(R.id.activity_basket_final_price)
    TextView mFinalPrice;
    @BindView(R.id.activity_basket_regular_price)
    TextView mRegularPrice;
    @BindView(R.id.activity_basket_promo)
    TextView mPromo;
    @BindView(R.id.activity_basket_list)
    RecyclerView recyclerView;
    @BindView(R.id.activity_basket_empty)
    TextView mEmptyBasket;

    private Basket mBasket;
    BasketAdapter basketAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        ButterKnife.bind(this);
        mBasket = Basket.getInstance();

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.basket);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        basketAdapter = new BasketAdapter(mBasket.getBooksQuantitiesMap());
        recyclerView.setAdapter(basketAdapter);

    }

    private void setUpListBasket(){
        if (!mBasket.getBooksQuantitiesMap().isEmpty()) {
            getCommercialOffersForBasket();
            recyclerView.setVisibility(View.VISIBLE);
            mEmptyBasket.setVisibility(View.GONE);
        } else {
            mPromo.setText(R.string.default_promotion);
            mFinalPrice.setText(R.string.default_total);
            recyclerView.setVisibility(View.GONE);
            mEmptyBasket.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onResume() {
        super.onResume();
        setUpListBasket();
        mRegularPrice.setText(String.format("Total: %.2f €", mBasket.getRegularPrice()));
    }

    @SuppressLint({"CheckResult", "DefaultLocale"})
    private void getCommercialOffersForBasket() {
        if (isOnline()) {
            BookInterface bookInterface = mRetrofit.create(BookInterface.class);

            Single<CommercialOffersResponse> singleCommercialOffer = bookInterface.getCommercialOffer(mBasket.getPromotionCode());

            singleCommercialOffer.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(commercialOffersResponse -> {
                        mPromo.setText(String.format("Promotion: -%.2f €", mBasket.getPromotionValue(commercialOffersResponse)));
                        float price = mBasket.applyBestCommercialOffer(commercialOffersResponse, mBasket.getRegularPrice());
                        mFinalPrice.setText(String.format("New Total: %.2f €", price));
                    }, Timber::d);
            }
    }

    @SuppressLint("DefaultLocale")
    public void updateBasket(){
        setUpListBasket();
        mRegularPrice.setText(String.format("Total: %.2f €", mBasket.getRegularPrice()));
        basketAdapter.notifyDataSetChanged();
    }

}
