package cdreyfus.xebia_henri_potier.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cdreyfus.xebia_henri_potier.HenriPotierApplication;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.activity.models.HenriPotierActivity;
import cdreyfus.xebia_henri_potier.interfaces.BookInterface;
import cdreyfus.xebia_henri_potier.models.Basket;
import cdreyfus.xebia_henri_potier.models.CommercialOffersArray;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

public class BasketActivity extends HenriPotierActivity {

    @BindView(R.id.activity_cart_final_price)
    TextView mTextFinalPrice;

    private Basket mBasket;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        mBasket = Basket.getInstance();

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.basket);

        if(!mBasket.getBooksQuantitiesMap().isEmpty()) {
            getCommercialOffersForBasket();
        }
    }

    private void getCommercialOffersForBasket() {
        if (((HenriPotierApplication) getApplication()).isOnline()) {

            Retrofit retrofit = ((HenriPotierApplication) getApplication()).getRetrofit();
            BookInterface bookInterface = retrofit.create(BookInterface.class);
            Call<CommercialOffersArray> call = bookInterface.getCommercialOffer(mBasket.getPromotionCode());
            call.enqueue(new Callback<CommercialOffersArray>() {
                @Override
                public void onResponse(@NonNull Call<CommercialOffersArray> call, @NonNull Response<CommercialOffersArray> response) {
                    if (response.isSuccessful()) {
                        float price = mBasket.applyBestCommercialOffer(response.body(), mBasket.getRegularPrice());
                        mTextFinalPrice.setText(String.format("%s €", price));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CommercialOffersArray> call, @NonNull Throwable t) {
                    Timber.d(t);
                }
            });
        }
    }

}
