package cdreyfus.xebia_henri_potier.book;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cuiweiyou.numberpickerdialog.NumberPickerDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.basket.Basket;
import cdreyfus.xebia_henri_potier.basket.BasketActivity;
import cdreyfus.xebia_henri_potier.utils.Utils;

public class BookActivity extends AppCompatActivity implements BookPresenter.View {

    @BindView(R.id.activity_book_cover)
    ImageView bookCover;
    @BindView(R.id.activity_book_price)
    TextView bookPrice;
    @BindView(R.id.activity_book_synopsis)
    TextView bookSynopsis;
    @BindView(R.id.activity_book_add_to_basket)
    Button buttonAddBookToBasket;
    @BindView(R.id.activity_book_edit_quantity_button)
    Button buttonEditQuantity;
    @BindView(R.id.activity_book_remove_from_basket_button)
    Button buttonRemoveFromBasket;

    private BookPresenter bookPresenter;
    private NumberPickerDialog mNumberPickerDialog;

    private Basket mBasket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.bookPresenter = new BookPresenter(this, getSharedPreferences(Utils.INSTANCE.getSHARED_PREFS(), Context.MODE_PRIVATE));

        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);

        if (getIntent().hasExtra(Utils.INSTANCE.getEXTRA_BOOK_ID())) {
            bookPresenter.initBook(getIntent().getStringExtra(Utils.INSTANCE.getEXTRA_BOOK_ID()));
        }

        if (getIntent().hasExtra(Utils.INSTANCE.getEXTRA_LIST_BOOKS())){
            mBasket = getIntent().getParcelableExtra(Utils.INSTANCE.getEXTRA_LIST_BOOKS());
        } else {
            mBasket = new Basket(new HashMap<>());
        }
        bookPresenter.initBasket(mBasket);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bookPresenter.getBook();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_basket, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_basket:
                Intent goToBasket = new Intent(BookActivity.this, BasketActivity.class);
                if(!mBasket.getBooksQuantitiesMap().isEmpty()) {
                    goToBasket.putExtra(Utils.INSTANCE.getEXTRA_LIST_BOOKS(), mBasket);
                }
                startActivity(goToBasket);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitre(String titre) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(titre);
    }

    @Override
    public void setCover(String cover) {
        Picasso.get().load(cover).into(bookCover);
    }

    @Override
    public void setResume(@org.jetbrains.annotations.Nullable ArrayList<String> resume) {
        StringBuilder synopsis = new StringBuilder();
        for (String part : resume) {
            synopsis.append(part).append("\n");
        }
        String fomatedResume = synopsis.substring(0, synopsis.lastIndexOf("\n"));
        bookSynopsis.setText(fomatedResume);
    }

    @Override
    public void setPrice(@Nullable Float price) {
        bookPrice.setText(String.format("%s €", price));
    }

    @Override
    public void setBookInBasket(int quantity) {
        buttonAddBookToBasket.setVisibility(View.GONE);
        buttonEditQuantity.setVisibility(View.VISIBLE);
        buttonRemoveFromBasket.setVisibility(View.VISIBLE);

        buttonEditQuantity.setText(String.format("%s (%s)", getString(R.string.edit_quantity), quantity));
    }

    @Override
    public void setNotInBasket() {
        buttonAddBookToBasket.setVisibility(View.VISIBLE);
        buttonEditQuantity.setVisibility(View.GONE);
        buttonRemoveFromBasket.setVisibility(View.GONE);
    }

    @Override
    public void showNumberPicker(String title, int quantity) {
        mNumberPickerDialog = new NumberPickerDialog(BookActivity.this,
                title,
                (picker, oldVal, newVal) -> {
                    bookPresenter.editQuantityinBasket(newVal);

                }, 10,
                1,
                quantity);

        mNumberPickerDialog.show();
    }

    @Override
    public void hideNumberPicker() {
        mNumberPickerDialog.hide();
    }

    @OnClick(R.id.activity_book_add_to_basket)
    void clickAddToBasket() {
        bookPresenter.addToBasket();
    }

    @OnClick(R.id.activity_book_remove_from_basket_button)
    void clickRemoveFromBasket() {
        bookPresenter.removeFromBasket();
    }

    @OnClick(R.id.activity_book_edit_quantity_button)
    void clickEditQuantity() {
        bookPresenter.selectQuantityInBasket();
    }


}
