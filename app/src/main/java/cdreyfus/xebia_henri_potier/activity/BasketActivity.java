package cdreyfus.xebia_henri_potier.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.activity.models.HenriPotierActivity;
import cdreyfus.xebia_henri_potier.interfaces.BookInterface;
import cdreyfus.xebia_henri_potier.models.Basket;
import cdreyfus.xebia_henri_potier.models.CommercialOffersResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class BasketActivity extends HenriPotierActivity {

    @BindView(R.id.activity_basket_final_price)
    TextView mFinalPrice;
    @BindView(R.id.activity_basket_regular_price)
    TextView mRegularPrice;
    @BindView(R.id.activity_basket_promo)
    TextView mPromo;
    private Basket mBasket;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        mBasket = Basket.getInstance();

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.basket);

        if (!mBasket.getBooksQuantitiesMap().isEmpty()) {
            getCommercialOffersForBasket();
        }

        mRegularPrice.setText(String.format("Total: %s €", mBasket.getRegularPrice()));
    }

    private void getCommercialOffersForBasket() {
        if (isOnline()) {

            BookInterface bookInterface = mRetrofit.create(BookInterface.class);
            Call<CommercialOffersResponse> call = bookInterface.getCommercialOffer(mBasket.getPromotionCode());
            call.enqueue(new Callback<CommercialOffersResponse>() {
                @Override
                public void onResponse(@NonNull Call<CommercialOffersResponse> call, @NonNull Response<CommercialOffersResponse> response) {
                    if (response.isSuccessful()) {
                        mPromo.setText(String.format("Promotion: %s", mBasket.getPromotionValue(response.body())));
                        float price = mBasket.applyBestCommercialOffer(response.body(), mBasket.getRegularPrice());
                        mFinalPrice.setText(String.format("New Total: %s €", price));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CommercialOffersResponse> call, @NonNull Throwable t) {
                    Timber.d(t);
                }
            });
        }
    }

}
