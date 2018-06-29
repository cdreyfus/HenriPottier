package cdreyfus.xebia_henri_potier.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.activity.models.HenriPotierActivity;
import cdreyfus.xebia_henri_potier.adapter.CatalogueAdapter;
import cdreyfus.xebia_henri_potier.basket.BasketActivity;
import cdreyfus.xebia_henri_potier.interfaces.BookInterface;
import cdreyfus.xebia_henri_potier.models.Book;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CatalogueActivity extends HenriPotierActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.activity_catalogue_list)
    RecyclerView recyclerView;
    @BindView(R.id.activity_catalogue_empty_db)
    TextView mEmptyDb;
    @BindView(R.id.activity_catalogue_frame_data)
    FrameLayout mFrameLayout;
    @BindView(R.id.activity_catalogue_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.activity_catalogue_progress_bar)
    ProgressBar progressBarRefreshList;

    private CatalogueAdapter catalogueAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.catalogue);
        refreshLayout.setOnRefreshListener(this);
        setView();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookDao.loadAll();
        catalogueAdapter = new CatalogueAdapter(this);
        recyclerView.setAdapter(catalogueAdapter);

        getCatalogue();
    }

    public void setView() {
        boolean isDbEmpty = bookDao.queryBuilder().list().isEmpty();
        mFrameLayout.setVisibility(isDbEmpty ? View.GONE : View.VISIBLE);
        mEmptyDb.setVisibility(isDbEmpty ? View.VISIBLE : View.GONE);

        progressBarRefreshList.setVisibility(View.GONE);
        refreshLayout.setRefreshing(false);
    }

    @SuppressLint("CheckResult")
    public void getCatalogue() {
        if (isOnline()) {

            BookInterface bookInterface = mRetrofit.create(BookInterface.class);
            Observable<List<Book>> bookObservable = bookInterface.getBooks();

            bookObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map((Function<List<Book>, Object>) books -> books)
                    .subscribe(o -> {
                        bookDao.insertOrReplaceInTx((Iterable<Book>) o);
                        catalogueAdapter.updateApdater();
                        setView();
                    }, Timber::d);
        } else {
            NotConnectedAlertDialog notConnectedAlertDialog = new NotConnectedAlertDialog(CatalogueActivity.this);
            notConnectedAlertDialog.show();
        }
    }

    @Override
    public void onRefresh() {
        Timber.i("refreshing....");
        mFrameLayout.setVisibility(View.GONE);
        progressBarRefreshList.setVisibility(View.VISIBLE);

        refreshLayout.postDelayed(this::getCatalogue, 1500);
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

}
