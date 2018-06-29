package cdreyfus.xebia_henri_potier.basket;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.models.Book;


public class BasketRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LinkedHashMap<Book, Integer> bookIntegerLinkedHashMap;
    private BasketPresenter basketPresenter;

    public BasketRecyclerAdapter(BasketPresenter basketPresenter) {
        this.basketPresenter = basketPresenter;
        bookIntegerLinkedHashMap = new LinkedHashMap<>();
    }

    @NonNull
    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_basket_view, parent, false);
        return new BasketItemHolder(view, basketPresenter);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder  holder, int position) {
        Book book = (new ArrayList<>(bookIntegerLinkedHashMap.entrySet())).get(position).getKey();
        int quantity = (new ArrayList<>(bookIntegerLinkedHashMap.entrySet())).get(position).getValue();

        BasketItemHolder basketItemHolder = (BasketItemHolder) holder;
        basketItemHolder.setItem(book, quantity);
    }

    @Override
    public int getItemCount() {
        return bookIntegerLinkedHashMap.size();
    }

    public void addAll(LinkedHashMap<Book, Integer> collection) {
        bookIntegerLinkedHashMap.putAll(collection);
    }
}