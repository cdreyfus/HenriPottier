package cdreyfus.xebia_henri_potier.catalogue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.basket.BasketActivity;
import cdreyfus.xebia_henri_potier.book.Book;
import cdreyfus.xebia_henri_potier.book.BookActivity;
import timber.log.Timber;

import static android.view.View.GONE;
import static cdreyfus.xebia_henri_potier.utils.Utils.EXTRA_BOOK_ID;

public class CatalogueActivity extends AppCompatActivity implements CataloguePresenter.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.activity_catalogue_list)
    RecyclerView recyclerView;
    @BindView(R.id.activity_catalogue_empty_db)
    TextView mEmptyDb;
    @BindView(R.id.activity_catalogue_frame_data)
    FrameLayout mFrameLayout;
    @BindView(R.id.activity_catalogue_refresh)
    SwipeRefreshLayout refreshLayout;

    private CatalogueAdapter catalogueAdapter;
    private CataloguePresenter cataloguePresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);
        ButterKnife.bind(this);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.catalogue);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout.setOnRefreshListener(this);

        cataloguePresenter = new CataloguePresenter(this, getApplicationContext());


        catalogueAdapter = new CatalogueAdapter(cataloguePresenter);
        recyclerView.setAdapter(catalogueAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cataloguePresenter.getCatalogue();
        cataloguePresenter.setView();
    }

    @Override
    public void onRefresh() {
        Timber.i("refreshing....");

        refreshLayout.postDelayed(() -> {
            cataloguePresenter.getCatalogue();
            cataloguePresenter.setView();
        }, 1500);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_basket, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_basket:
                Intent goToBasket = new Intent(CatalogueActivity.this, BasketActivity.class);
                startActivity(goToBasket);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showCatalogue(List<Book> bookList) {
        refreshLayout.setRefreshing(false);

        catalogueAdapter.addAll(bookList);
        catalogueAdapter.notifyDataSetChanged();
        mEmptyDb.setVisibility(GONE);
    }

    @Override
    public void showEmpty() {
        refreshLayout.setRefreshing(false);
        mEmptyDb.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBookSelected(String isbn) {
        Intent startBook = new Intent(CatalogueActivity.this, BookActivity.class);
        startBook.putExtra(EXTRA_BOOK_ID, isbn);
        startActivity(startBook);
    }

    @Override
    public void notConnected() {
        new NotConnectedAlertDialog(CatalogueActivity.this).show();
    }

    protected class NotConnectedAlertDialog extends AlertDialog {

        NotConnectedAlertDialog(@NonNull Context context) {
            super(context);
            setCancelable(false);

            setTitle(getString(R.string.no_internet_connection));
            setMessage(getString(R.string.message_not_connected));

            setButton(BUTTON_POSITIVE, getString(R.string.ok), (dialog, which) -> {
                dismiss();
                onBackPressed();
            });
        }
    }
}
