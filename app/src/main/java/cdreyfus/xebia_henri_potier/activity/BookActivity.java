package cdreyfus.xebia_henri_potier.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cdreyfus.xebia_henri_potier.HenriPotierApplication;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.activity.models.HenriPotierActivity;
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
    @BindView(R.id.activity_book_number_picker)
    NumberPicker numberPicker;

    private Book mBook;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);

        numberPicker.setMinValue(1);
        numberPicker.setWrapSelectorWheel(true);

        BookDao bookDao = ((HenriPotierApplication) getApplication()).getDaoSession().getBookDao();
        bookDao.loadAll();

        if (getIntent().hasExtra(EXTRA_BOOK_ID)) {
            getIntent().getStringExtra(EXTRA_BOOK_ID);
            mBook = bookDao.queryBuilder().where(BookDao.Properties.Isbn.eq(getIntent().getStringExtra(EXTRA_BOOK_ID))).build().unique();
            setUpView(mBook);
        }
    }

    private void setUpView(Book book) {
        Picasso.get().load(book.getCover()).into(bookCover);
        bookPrice.setText(String.format("%s€", book.getPrice()));
        bookSynopsis.setText(book.getSynopsis());
        Objects.requireNonNull(getSupportActionBar()).setTitle(book.getTitle());
    }

    @OnClick(R.id.activity_book_add_to_basket)
    public void addToBasket() {
        Basket.getInstance().addBookToBasket(mBook);
    }

//    @OnClick(R.id.activity_book_number_picker)
//    private void showNumberPicker(){
//    }
}
