package cdreyfus.xebia_henri_potier.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cdreyfus.xebia_henri_potier.models.Book;
import cdreyfus.xebia_henri_potier.views.CatalogueItemView;

public class CatalogueItemAdapter extends BaseAdapter {

    private List<Book> mBookList;
    private Context mContext;

    public CatalogueItemAdapter(Context context, List<Book> bookList) {
        mContext = context;
        mBookList = bookList;
    }

    @Override
    public int getCount() {
        return mBookList.size();
    }

    @Override
    public Book getItem(int position) {
        return mBookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CatalogueItemView view;
        final Book book = mBookList.get(position);
        if (convertView == null) {
            view = new CatalogueItemView(mContext);
        } else {
            view = (CatalogueItemView) convertView;
        }
        view.setUpItem(book);
        return view;
    }
}
