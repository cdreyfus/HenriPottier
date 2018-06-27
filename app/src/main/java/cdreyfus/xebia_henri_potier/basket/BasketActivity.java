package cdreyfus.xebia_henri_potier.basket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cdreyfus.xebia_henri_potier.R;

public class BasketActivity extends AppCompatActivity implements BasketPresenter.View {

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.basket);

    }


    @Override
    public void updateBasketContent() {

    }

    @Override
    public void updatePrices(float regular, float promo, float finalPrice) {
        mRegularPrice.setText(String.format(Locale.ENGLISH, "Total: %.2f €", regular));
        mPromo.setText(String.format(Locale.ENGLISH,"Promotion: -%.2f €", promo);
        mFinalPrice.setText(String.format(Locale.ENGLISH,"New Total: %.2f €", finalPrice));
    }
}

