package cdreyfus.xebia_henri_potier.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.cuiweiyou.numberpickerdialog.NumberPickerDialog;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.basket.BasketActivity;

import static cdreyfus.xebia_henri_potier.utils.Utils.EXTRA_BOOK_ID;

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
    private String bookIsbn;
    private NumberPickerDialog mNumberPickerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.bookPresenter = new BookPresenter(this, getApplicationContext());

        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);

        if (getIntent().hasExtra(EXTRA_BOOK_ID)) {
            bookIsbn = getIntent().getStringExtra(EXTRA_BOOK_ID);
        }

        bookPresenter.getBook(bookIsbn);
    }

    private NumberPicker.OnValueChangeListener initNumberPickerDialog() {
        NumberPicker.OnValueChangeListener onValueChangeListener = (picker, oldVal, newVal) -> {
            bookPresenter.editQuantityinBasket(bookIsbn, newVal);
        };
        return onValueChangeListener;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_basket, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_basket:
                Intent goToBasket = new Intent(BookActivity.this, BasketActivity.class);
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
    public void setResume(String resume) {
        bookSynopsis.setText(resume);
    }

    @Override
    public void setPrice(float price) {
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
        mNumberPickerDialog = new NumberPickerDialog(BookActivity.this, title, initNumberPickerDialog(), 10, 1, quantity);
        mNumberPickerDialog.show();
    }

    @Override
    public void hideNumberPicker() {
        mNumberPickerDialog.hide();
    }

    @OnClick(R.id.activity_book_add_to_basket)
    void clickAddToBasket() {
        bookPresenter.addToBasket(bookIsbn);
    }

    @OnClick(R.id.activity_book_remove_from_basket_button)
    void clickRemoveFromBasket() {
        bookPresenter.removeFromBasket(bookIsbn);
    }

    @OnClick(R.id.activity_book_edit_quantity_button)
    void clickEditQuantity() {
        bookPresenter.initNumberPicker(bookIsbn);
    }

}
