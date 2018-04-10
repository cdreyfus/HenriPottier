package cdreyfus.xebia_henri_potier.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cdreyfus.xebia_henri_potier.HenriPotierApplication;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.activity.models.HenriPotierActivity;
import cdreyfus.xebia_henri_potier.dialog.NumberPickerDialog;
import cdreyfus.xebia_henri_potier.models.Basket;
import cdreyfus.xebia_henri_potier.models.Book;
import cdreyfus.xebia_henri_potier.models.BookDao;

import static cdreyfus.xebia_henri_potier.utils.Utils.EXTRA_BOOK_ID;

public class BookActivity extends HenriPotierActivity {

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

    private Book mBook;
    private Basket mBasket;
    private NumberPickerDialog mNumberPickerDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);
        mBasket = Basket.getInstance();

        BookDao bookDao = ((HenriPotierApplication) getApplication()).getDaoSession().getBookDao();
        bookDao.loadAll();

        if (getIntent().hasExtra(EXTRA_BOOK_ID)) {
            getIntent().getStringExtra(EXTRA_BOOK_ID);
            mBook = bookDao.queryBuilder().where(BookDao.Properties.Isbn.eq(getIntent().getStringExtra(EXTRA_BOOK_ID))).build().unique();
            setUpView(mBook);
        }
    }

    private void initNumberPickerDialog() {
        mNumberPickerDialog = new NumberPickerDialog(BookActivity.this, mBasket.getBooksQuantitiesMap().get(mBook));
        mNumberPickerDialog.setTitle(mBook.getTitle());
        mNumberPickerDialog.setButton1(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberPickerDialog.dismiss();
                mBasket.editQuantityBook(mBook, mNumberPickerDialog.getValue());
            }
        });
        mNumberPickerDialog.setButton2(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberPickerDialog.dismiss();
            }
        });
    }

    private void setUpView(Book book) {
        Picasso.get().load(book.getCover()).into(bookCover);
        bookPrice.setText(String.format("%s €", book.getPrice()));
        bookSynopsis.setText(book.getSynopsis());
        Objects.requireNonNull(getSupportActionBar()).setTitle(book.getTitle());
        setButtonView();

    }

    private void setButtonView() {
        buttonAddBookToBasket.setVisibility(mBasket.getBooksQuantitiesMap().containsKey(mBook) ? View.GONE : View.VISIBLE);
        buttonEditQuantity.setVisibility(mBasket.getBooksQuantitiesMap().containsKey(mBook) ? View.VISIBLE : View.GONE);
        buttonRemoveFromBasket.setVisibility(mBasket.getBooksQuantitiesMap().containsKey(mBook) ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.activity_book_add_to_basket)
    public void clickAddToBasket() {
        mBasket.addBookToBasket(mBook);
        setButtonView();
    }

    @OnClick(R.id.activity_book_remove_from_basket_button)
    public void clickRemoveFromBasket() {
        mBasket.deleteBookFromBasket(mBook);
        setButtonView();
    }

    @OnClick(R.id.activity_book_edit_quantity_button)
    public void editQuantity() {
//        initNumberPickerDialog();
//        mNumberPickerDialog.show();
        AlertDialog.Builder builder = new AlertDialog.Builder(BookActivity.this)
                .setTitle(mBook.getTitle());
        builder.create().show();
    }


}
