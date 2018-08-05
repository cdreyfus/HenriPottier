package cdreyfus.xebia_henri_potier.basket;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.book.Book;


public class BasketRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private HashMap<Book, Integer> bookIntegerLinkedHashMap;
    private BasketPresenter basketPresenter;

    public BasketRecyclerAdapter(BasketPresenter basketPresenter) {
        bookIntegerLinkedHashMap = new HashMap<>();
        this.basketPresenter = basketPresenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_basket_view, parent, false);

        return new BasketItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Book book = (new ArrayList<>(bookIntegerLinkedHashMap.entrySet())).get(position).getKey();
        int quantity = (new ArrayList<>(bookIntegerLinkedHashMap.entrySet())).get(position).getValue();

        BasketItemHolder basketItemHolder = (BasketItemHolder) holder;
        basketItemHolder.setItem(book, quantity);

        basketItemHolder.itemView.setOnClickListener(v -> {
            basketPresenter.initBook(book);
            basketPresenter.setNumberPicker();
        });
    }

    @Override
    public int getItemCount() {
        return bookIntegerLinkedHashMap.size();
    }

    public void addAll(HashMap<Book, Integer> collection) {
        bookIntegerLinkedHashMap.putAll(collection);
    }

}