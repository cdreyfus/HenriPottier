package cdreyfus.xebia_henri_potier.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cdreyfus.xebia_henri_potier.HenriPotierApplication;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.activity.models.HenriPotierActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);

        BookDao bookDao = ((HenriPotierApplication) getApplication()).getDaoSession().getBookDao();
        bookDao.loadAll();

        if(getIntent().hasExtra(EXTRA_BOOK_ID)){
            getIntent().getStringExtra(EXTRA_BOOK_ID);
            Book mBook = bookDao.queryBuilder().where(BookDao.Properties.Isbn.eq(getIntent().getStringExtra(EXTRA_BOOK_ID))).build().unique();
            setUpView(mBook);
        }
    }

    private void setUpView(Book book){
        Picasso.get().load(book.getImageUrl()).into(bookCover);
        bookPrice.setText(String.format("%s€",book.getPrice()));
        bookSynopsis.setText(book.getSynopsis());
        Objects.requireNonNull(getSupportActionBar()).setTitle(book.getTitle());
    }
}
