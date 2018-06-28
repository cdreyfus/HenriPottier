package cdreyfus.xebia_henri_potier.basket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.adapter.BasketAdapter;
import cdreyfus.xebia_henri_potier.models.Book;

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

    private BasketPresenter basketPresenter;
    private BasketAdapter basketAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.basket);

        initRecycler();
        basketPresenter = new BasketPresenter(this);
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        basketAdapter = new BasketAdapter(new LinkedHashMap<>());
        recyclerView.setAdapter(basketAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        basketPresenter.setPrices();
    }

    @Override
    public void updateBasketContent() {

    }

    @Override
    public void updatePrices(float regular, float promo, float finalPrice) {
        mRegularPrice.setText(String.format(Locale.ENGLISH, "Total: %.2f €", regular));
        mPromo.setText(String.format(Locale.ENGLISH,"Promotion: -%.2f €", promo));
        mFinalPrice.setText(String.format(Locale.ENGLISH,"New Total: %.2f €", finalPrice));
    }
}

