package cdreyfus.xebia_henri_potier.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.book.Book;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ItemHolder> {

    private LinkedHashMap<Book, Integer> bookIntegerLinkedHashMap;

    public BasketAdapter(LinkedHashMap<Book, Integer> basketValues) {
        this.bookIntegerLinkedHashMap = basketValues;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_basket_view, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Book book = (new ArrayList<>(bookIntegerLinkedHashMap.entrySet())).get(position).getKey();
        int quantity = (new ArrayList<>(bookIntegerLinkedHashMap.entrySet())).get(position).getValue();

        holder.itemLabel.setText(book.getTitle());
        Picasso.get().load(book.getCover()).into(holder.imageView);
        holder.itemLabel.setText(book.getTitle());
        holder.itemPrice.setText(String.format("%s €", book.getPrice()));
        holder.itemQuantity.setText(String.format("x %s", quantity));
    }

    @Override
    public int getItemCount() {
        return bookIntegerLinkedHashMap.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.basket_item_label)
        TextView itemLabel;
        @BindView(R.id.basket_item_image)
        ImageView imageView;
        @BindView(R.id.basket_item_price)
        TextView itemPrice;
        @BindView(R.id.item_basket_quantity)
        TextView itemQuantity;


        ItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
