package cdreyfus.xebia_henri_potier.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.models.Book;
import cdreyfus.xebia_henri_potier.views.BasketItemView;

public class BasketAdapter extends RecyclerView.Adapter {

    private LinkedHashMap<Book, Integer> bookIntegerLinkedHashMap;

    public BasketAdapter(LinkedHashMap<Book, Integer> basketValues) {
        bookIntegerLinkedHashMap = basketValues;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_basket_view, parent, false);
        return new BasketItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BasketItemView) holder).setUpItem((new ArrayList<>(bookIntegerLinkedHashMap.entrySet())).get(position));
    }

    @Override
    public int getItemCount() {
        return bookIntegerLinkedHashMap.size();
    }

}
