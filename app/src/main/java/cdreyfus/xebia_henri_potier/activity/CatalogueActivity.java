package cdreyfus.xebia_henri_potier.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cdreyfus.xebia_henri_potier.HenriPotierApplication;
import cdreyfus.xebia_henri_potier.R;
import cdreyfus.xebia_henri_potier.activity.models.HenriPotierActivity;
import cdreyfus.xebia_henri_potier.adapter.CatalogueItemAdapter;
import cdreyfus.xebia_henri_potier.interfaces.BookInterface;
import cdreyfus.xebia_henri_potier.models.Book;
import cdreyfus.xebia_henri_potier.models.BookDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

public class CatalogueActivity extends HenriPotierActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.activity_catalogue_list_view)
    ListView listView;
    @BindView(R.id.activity_catalogue_empty_db)
    TextView mEmptyDb;
    @BindView(R.id.activity_catalogue_frame_data)
    FrameLayout mFrameLayout;
    @BindView(R.id.activity_catalogue_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.activity_catalogue_progress_bar)
    ProgressBar progressBarRefreshList;

    private CatalogueItemAdapter catalogueItemAdapter;
    private BookDao bookDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(R.string.catalogue);

        bookDao = ((HenriPotierApplication) getApplication()).getDaoSession().getBookDao();
        bookDao.loadAll();
        List<Book> bookList = bookDao.queryBuilder().list();
        catalogueItemAdapter = new CatalogueItemAdapter(CatalogueActivity.this, bookList);
        listView.setAdapter(catalogueItemAdapter);

        refreshLayout.setOnRefreshListener(this);
        getCatalogue();
    }

    private void getCatalogue() {

        if (((HenriPotierApplication) getApplication()).isOnline()) {

            mFrameLayout.setVisibility(View.GONE);
            progressBarRefreshList.setVisibility(View.VISIBLE);

            Retrofit retrofit = ((HenriPotierApplication) getApplication()).getRetrofit();
            BookInterface bookInterface = retrofit.create(BookInterface.class);
            Call<List<Book>> call = bookInterface.getBooks();
            call.enqueue(new Callback<List<Book>>() {
                @Override
                public void onResponse(@NonNull Call<List<Book>> call, @NonNull Response<List<Book>> response) {
                    bookDao.insertOrReplaceInTx(response.body());

                    mFrameLayout.setVisibility(View.VISIBLE);
                    progressBarRefreshList.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);

                    catalogueItemAdapter.notifyDataSetChanged();
                    mEmptyDb.setVisibility(catalogueItemAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
                    listView.setVisibility(catalogueItemAdapter.getCount() == 0 ? View.GONE : View.VISIBLE);
                }

                @Override
                public void onFailure(@NonNull Call<List<Book>> call, @NonNull Throwable t) {
                    Timber.d(t);
                }
            });
        } else {
            mFrameLayout.setVisibility(View.VISIBLE);
            progressBarRefreshList.setVisibility(View.GONE);

            catalogueItemAdapter.notifyDataSetChanged();
            mEmptyDb.setVisibility(catalogueItemAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
            listView.setVisibility(catalogueItemAdapter.getCount() == 0 ? View.GONE : View.VISIBLE);
        }
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
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        catalogueItemAdapter.notifyDataSetChanged();
        mEmptyDb.setVisibility(catalogueItemAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
        listView.setVisibility(catalogueItemAdapter.getCount() == 0 ? View.GONE : View.VISIBLE);
    }
}
