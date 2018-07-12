package cdreyfus.xebia_henri_potier.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cdreyfus.xebia_henri_potier.HenriPotierApplication;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.book.Book;
import cdreyfus.xebia_henri_potier.book.BookDao;
import cdreyfus.xebia_henri_potier.views.CatalogueItemView;

public class CatalogueAdapter extends RecyclerView.Adapter {

    private List<Book> bookList;
    private BookDao bookDao;

    public CatalogueAdapter(Context context) {
        bookDao = ((HenriPotierApplication) context.getApplicationContext()).getDaoSession().getBookDao();
        bookDao.loadAll();
        bookList = bookDao.queryBuilder().list();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_catalogue_view, parent, false);
        return new CatalogueItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CatalogueItemView) holder).setUpItem(bookList.get(position));
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void updateApdater() {
        bookList.clear();
        bookList = bookDao.queryBuilder().list();
        notifyDataSetChanged();
    }
}
