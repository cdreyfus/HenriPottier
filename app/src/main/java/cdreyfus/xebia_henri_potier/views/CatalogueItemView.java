package cdreyfus.xebia_henri_potier.views;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.book.Book;
import cdreyfus.xebia_henri_potier.book.BookActivity;

import static cdreyfus.xebia_henri_potier.utils.Utils.EXTRA_BOOK_ID;

public class CatalogueItemView extends RecyclerView.ViewHolder {


    @BindView(R.id.catalogue_item_label)
    TextView itemLabel;
    @BindView(R.id.catalogue_item_image)
    ImageView imageView;
    @BindView(R.id.catalogue_item_price)
    TextView itemPrice;

    private Book mBook;

    public CatalogueItemView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setUpItem(Book book) {
        Picasso.get().load(book.getCover()).into(imageView);
        itemLabel.setText(book.getTitle());
        itemPrice.setText(String.format("%s €", book.getPrice()));
        mBook = book;
    }


    @OnClick(R.id.catalogue_item_layout)
    public void onClick() {
        Intent startBook = new Intent(itemView.getContext(), BookActivity.class);
        startBook.putExtra(EXTRA_BOOK_ID, mBook.getIsbn());
        itemView.getContext().startActivity(startBook);
    }
}
