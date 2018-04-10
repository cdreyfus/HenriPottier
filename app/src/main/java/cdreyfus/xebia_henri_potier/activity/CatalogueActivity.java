package cdreyfus.xebia_henri_potier.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import cdreyfus.xebia_henri_potier.interfaces.BookInterface;
import cdreyfus.xebia_henri_potier.models.Book;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    public void setView(){
        boolean isDbEmpty = bookDao.queryBuilder().list().isEmpty();
        mFrameLayout.setVisibility(isDbEmpty ? View.GONE : View.VISIBLE);
        mEmptyDb.setVisibility(isDbEmpty ? View.VISIBLE:View.GONE);

        progressBarRefreshList.setVisibility(View.GONE);
        refreshLayout.setRefreshing(false);
    }

    public void getCatalogue() {

        BookInterface bookInterface = mRetrofit.create(BookInterface.class);
        Call<List<Book>> call = bookInterface.getBooks();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    bookDao.insertOrReplaceInTx(response.body());
                    catalogueAdapter.updateApdater();
                    setView();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Timber.d(t);

            }
        });
    }

    @Override
    public void onRefresh() {
        Timber.i("refreshing....");
        mFrameLayout.setVisibility(View.GONE);
        progressBarRefreshList.setVisibility(View.VISIBLE);

        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                getCatalogue();
            }
        }, 1500);
    }
}
