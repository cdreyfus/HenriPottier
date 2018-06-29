package cdreyfus.xebia_henri_potier.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cuiweiyou.numberpickerdialog.NumberPickerDialog;
import com.squareup.picasso.Picasso;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.models.Book;

public class BasketItemView extends View {


    @BindView(R.id.basket_item_label)
    TextView itemLabel;
    @BindView(R.id.basket_item_image)
    ImageView imageView;
    @BindView(R.id.basket_item_price)
    TextView itemPrice;
    @BindView(R.id.item_basket_quantity)
    TextView itemQuantity;

    private NumberPickerDialog mNumberPickerDialog;

    public BasketItemView(Context context) {
        super(context);
        ButterKnife.bind(this);
    }

    public void bindView(Map.Entry<Book, Integer> entry) {
        Book mBook = entry.getKey();
        Picasso.get().load(mBook.getCover()).into(imageView);
        itemLabel.setText(mBook.getTitle());
        itemPrice.setText(String.format("%s €", mBook.getPrice()));
        itemQuantity.setText(String.format("x %s", entry.getValue()));
      //  initNumberPickerDialog();

    }

//    private void initNumberPickerDialog() {
//        NumberPicker.OnValueChangeListener onValueChangeListener = (picker, oldVal, newVal) -> {
//            mBasket.editQuantityBook(mBook, newVal);
//            ((BasketActivity) itemView.getContext()).updateBasket();
//        };
//
//        DialogInterface.OnClickListener goToBook = (dialog, which) -> {
//            dialog.dismiss();
//            Intent startBook = new Intent(itemView.getContext(), BookActivity.class);
//            startBook.putExtra(EXTRA_BOOK_ID, mBook.getIsbn());
//        };
//
//        DialogInterface.OnClickListener removeFromBasket = (dialog, which) -> {
//            dialog.dismiss();
//            mBasket.deleteBookFromBasket(mBook);
//            ((BasketActivity) itemView.getContext()).updateBasket();
//        };
//
//        mNumberPickerDialog = new NumberPickerDialog(itemView.getContext(),
//                itemView.getContext().getString(R.string.edit_quantity),
//                onValueChangeListener,
//                10,
//                1,
//                mBasket.getBooksQuantitiesMap().get(mBook));
//        mNumberPickerDialog.setNegativeButton(itemView.getContext().getString(R.string.remove_from_basket), removeFromBasket);
//        mNumberPickerDialog.setNeutralButton(itemView.getContext().getString(R.string.see_book), goToBook);
//    }

//    @OnClick(R.id.basket_item_layout)
//    public void onClick() {
//        mNumberPickerDialog.show();
//    }
}
