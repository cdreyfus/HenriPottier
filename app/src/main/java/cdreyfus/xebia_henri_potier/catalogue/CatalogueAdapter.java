package cdreyfus.xebia_henri_potier.catalogue;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.book.Book;

public class CatalogueAdapter extends RecyclerView.Adapter {

    private List<Book> bookList;
    private CataloguePresenter cataloguePresenter;

    public CatalogueAdapter(CataloguePresenter cataloguePresenter) {
        this.cataloguePresenter = cataloguePresenter;
        bookList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_catalogue_view, parent, false);
        return new CatalogueHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Book book = bookList.get(position);
        CatalogueHolder catalogueHolder = (CatalogueHolder) holder;
        catalogueHolder.setUpItem(book);
        catalogueHolder.itemView.setOnClickListener(v -> cataloguePresenter.clickOnBook(book.getIsbn()));
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void addAll(List<Book> collection) {
        bookList.clear();
        bookList.addAll(collection);
    }

    class CatalogueHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.catalogue_item_label)
        TextView itemLabel;
        @BindView(R.id.catalogue_item_image)
        ImageView imageView;
        @BindView(R.id.catalogue_item_price)
        TextView itemPrice;


        CatalogueHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setUpItem(Book book) {
            Picasso.get().load(book.getCover()).into(imageView);
            itemLabel.setText(book.getTitle());
            itemPrice.setText(String.format("%s €", book.getPrice()));
        }
    }
}
