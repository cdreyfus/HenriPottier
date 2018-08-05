package cdreyfus.xebia_henri_potier.basket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cuiweiyou.numberpickerdialog.NumberPickerDialog;

import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.book.Book;

import static cdreyfus.xebia_henri_potier.utils.Utils.EXTRA_BOOK_ID;
import static cdreyfus.xebia_henri_potier.utils.Utils.EXTRA_LIST_BOOKS;

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
    private BasketRecyclerAdapter basketAdapter;
    private NumberPickerDialog numberPickerDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.basket);
        basketPresenter = new BasketPresenter(this, getApplicationContext());

        if (getIntent().hasExtra(EXTRA_LIST_BOOKS)){
//            Basket basket = getIntent().getParcelableExtra(EXTRA_BOOK_ID);
            Basket basket = (Basket) getIntent().getExtras().get(EXTRA_LIST_BOOKS);



            basketPresenter.setBasket(basket);
        }

        basketAdapter = new BasketRecyclerAdapter(basketPresenter);
        initRecycler();

    }

    @Override
    protected void onResume() {
        super.onResume();
        basketPresenter.updateBasketContent();
        basketPresenter.setPrices();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(basketAdapter);
    }

    @Override
    public void setRegularPrice(float regularPrice) {
        mRegularPrice.setText(String.format(Locale.ENGLISH, "Total: %.2f €", regularPrice));
    }

    @Override
    public void setPromoValue(float promoValue) {
        mPromo.setText(String.format(Locale.ENGLISH, "Promotion: %.2f €", promoValue));
    }

    @Override
    public void setFinalPrice(float finalPrice) {
        mFinalPrice.setText(String.format(Locale.ENGLISH, "New Total: %.2f €", finalPrice));
    }


    @Override
    public void showBooks(HashMap<Book, Integer> hashMap) {
        basketAdapter.addAll(hashMap);
        basketAdapter.notifyDataSetChanged();

        mEmptyBasket.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        mEmptyBasket.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showNumberPicker(String title, int quantity) {
        numberPickerDialog = new NumberPickerDialog(this,
                title,
                (picker, oldVal, newVal) -> {
                    basketPresenter.editQuantityBook(newVal);
                    basketPresenter.updateBasketContent();
                    basketPresenter.setPrices();
                }, 10,
                1,
                quantity);

        numberPickerDialog.show();
    }


    @Override
    public void hideNumberPicker() {
        numberPickerDialog.hide();
    }
}

