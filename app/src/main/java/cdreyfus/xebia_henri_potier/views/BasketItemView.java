package cdreyfus.xebia_henri_potier.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.cuiweiyou.numberpickerdialog.NumberPickerDialog;
import com.squareup.picasso.Picasso;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.activity.BasketActivity;
import cdreyfus.xebia_henri_potier.activity.BookActivity;
import cdreyfus.xebia_henri_potier.models.Basket;
import cdreyfus.xebia_henri_potier.models.Book;

import static cdreyfus.xebia_henri_potier.utils.Utils.EXTRA_BOOK_ID;

public class BasketItemView extends RecyclerView.ViewHolder {


    @BindView(R.id.basket_item_label)
    TextView itemLabel;
    @BindView(R.id.basket_item_image)
    ImageView imageView;
    @BindView(R.id.basket_item_price)
    TextView itemPrice;
    @BindView(R.id.item_basket_quantity)
    TextView itemQuantity;

    private Book mBook;
    private Basket mBasket;
    private NumberPickerDialog mNumberPickerDialog;

    public BasketItemView(View itemView) {
        super(itemView);
        mBasket = Basket.getInstance();
        ButterKnife.bind(this, itemView);
    }

    public void setUpItem(Map.Entry<Book, Integer> entry) {
        mBook = entry.getKey();
        Picasso.get().load(mBook.getCover()).into(imageView);
        itemLabel.setText(mBook.getTitle());
        itemPrice.setText(String.format("%s €", mBook.getPrice()));
        itemQuantity.setText(String.format("x %s", entry.getValue()));
        initNumberPickerDialog();

    }

    private void initNumberPickerDialog() {
        NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mBasket.editQuantityBook(mBook, newVal);
                ((BasketActivity) itemView.getContext()).updateBasket();
            }
        };

        DialogInterface.OnClickListener goToBook = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent startBook = new Intent(itemView.getContext(), BookActivity.class);
                startBook.putExtra(EXTRA_BOOK_ID, mBook.getIsbn());
            }
        };

        DialogInterface.OnClickListener removeFromBasket = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mBasket.deleteBookFromBasket(mBook);
                ((BasketActivity) itemView.getContext()).updateBasket();
            }
        };

        mNumberPickerDialog = new NumberPickerDialog(itemView.getContext(),
                itemView.getContext().getString(R.string.edit_quantity),
                onValueChangeListener,
                10,
                1,
                mBasket.getBooksQuantitiesMap().get(mBook));
        mNumberPickerDialog.setNegativeButton(itemView.getContext().getString(R.string.remove_from_basket), removeFromBasket);
        mNumberPickerDialog.setNeutralButton(itemView.getContext().getString(R.string.see_book), goToBook);
    }

    @OnClick(R.id.basket_item_layout)
    public void onClick() {
        mNumberPickerDialog.show();
    }
}
