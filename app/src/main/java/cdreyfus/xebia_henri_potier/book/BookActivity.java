package cdreyfus.xebia_henri_potier.book;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cdreyfus.xebia_henri_potier.R;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.bookPresenter = new BookPresenter(this, getApplicationContext());
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);

        if (getIntent().hasExtra(EXTRA_BOOK_ID)) {
            bookPresenter.getBook( getIntent().getStringExtra(EXTRA_BOOK_ID));
        }
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

}
