package cdreyfus.xebia_henri_potier.views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.activity.BookActivity;
import cdreyfus.xebia_henri_potier.activity.CatalogueActivity;
import cdreyfus.xebia_henri_potier.models.Book;

import static cdreyfus.xebia_henri_potier.utils.Utils.EXTRA_BOOK_ID;

public class CatalogueItemView extends LinearLayout{


    private final Context context;
    @BindView(R.id.catalogue_item_label) TextView itemLabel;
    @BindView(R.id.catalogue_item_image)ImageView imageView;
    @BindView(R.id.catalogue_item_price) TextView itemPrice;

    private Book mBook;

    public CatalogueItemView(Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ButterKnife.bind(Objects.requireNonNull(inflater).inflate(R.layout.item_catalogue_view, this));
    }

    public void setUpItem(Book book){
        Picasso.get().load(book.getCover()).into(imageView);
        itemLabel.setText(book.getTitle());
        itemPrice.setText(String.format("%s€", book.getPrice()));
        mBook = book;
    }

    @OnClick(R.id.catalogue_item_layout)
    public void onClick() {
        Intent startBook = new Intent(context, BookActivity.class);
        startBook.putExtra(EXTRA_BOOK_ID, mBook.getIsbn());
        context.startActivity(startBook);
    }
}
