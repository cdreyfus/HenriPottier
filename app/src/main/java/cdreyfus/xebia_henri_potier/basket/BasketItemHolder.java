package cdreyfus.xebia_henri_potier.basket;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.book.Book;

public class BasketItemHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.basket_item_label)
    TextView itemLabel;
    @BindView(R.id.basket_item_image)
    ImageView imageView;
    @BindView(R.id.basket_item_price)
    TextView itemPrice;
    @BindView(R.id.item_basket_quantity)
    TextView itemQuantity;

    private final BasketPresenter basketPresenter;


    public BasketItemHolder(View itemView, BasketPresenter basketPresenter) {
        super(itemView);
        this.basketPresenter = basketPresenter;
        ButterKnife.bind(this, itemView);
    }

    public void setItem(Book book, int quantity){
        Picasso.get().load(book.getCover()).into(imageView);
        itemLabel.setText(book.getTitle());
        itemPrice.setText(String.format("%s €", book.getPrice()));
        itemQuantity.setText(String.format("x %s", quantity));
    }
}
