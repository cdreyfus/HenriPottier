package cdreyfus.xebia_henri_potier.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cdreyfus.xebia_henri_potier.HenriPotierApplication;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.activity.models.HenriPotierActivity;
import cdreyfus.xebia_henri_potier.interfaces.BookInterface;
import cdreyfus.xebia_henri_potier.models.Basket;
import cdreyfus.xebia_henri_potier.models.CommercialOffer;
import cdreyfus.xebia_henri_potier.models.Offers;
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

        getCommercialOffersForBasket();

    }

    private void getCommercialOffersForBasket() {
        if (((HenriPotierApplication) getApplication()).isOnline()) {


            Retrofit retrofit = ((HenriPotierApplication) getApplication()).getRetrofit();
            BookInterface bookInterface = retrofit.create(BookInterface.class);
            Call<List<CommercialOffer>> call = bookInterface.getCommercialOffer(mBasket.getPromotionCode());
            call.enqueue(new Callback<List<CommercialOffer>>() {
                @Override
                public void onResponse(@NonNull Call<List<CommercialOffer>> call, @NonNull Response<List<CommercialOffer>> response) {
                    if (response.isSuccessful()) {
                        List<CommercialOffer> commercialOfferList = response.body();
//                        mTextFinalPrice.setText(String.format("%s €", mBasket.applyBestCommercialOffer(commercialOfferList, mBasket.getRegularPrice())));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<CommercialOffer>> call, @NonNull Throwable t) {
                    Timber.d(t);
                }
            });
        }
    }

}
