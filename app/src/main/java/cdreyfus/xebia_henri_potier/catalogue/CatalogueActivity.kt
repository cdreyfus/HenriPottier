package cdreyfus.xebia_henri_potier.catalogue

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import butterknife.ButterKnife
import cdreyfus.xebia_henri_potier.R
import cdreyfus.xebia_henri_potier.basket.BasketActivity
import cdreyfus.xebia_henri_potier.book.Book
import cdreyfus.xebia_henri_potier.book.BookActivity
import cdreyfus.xebia_henri_potier.utils.Utils.EXTRA_BOOK_ID
import kotlinx.android.synthetic.main.activity_catalogue.*
import timber.log.Timber

class CatalogueActivity : AppCompatActivity(), CataloguePresenter.View, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var catalogueAdapter: CatalogueAdapter
    private lateinit var cataloguePresenter: CataloguePresenter

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogue)
        ButterKnife.bind(this)

        supportActionBar!!.setTitle(R.string.catalogue)

        activity_catalogue_list.layoutManager = LinearLayoutManager(this)
        activity_catalogue_refresh.setOnRefreshListener(this)

        cataloguePresenter = CataloguePresenter(this, applicationContext)


        catalogueAdapter = CatalogueAdapter(cataloguePresenter)
        activity_catalogue_list.adapter = catalogueAdapter
    }

    override fun onResume() {
        super.onResume()
        cataloguePresenter.getCatalogue()
        cataloguePresenter.setView()
    }

    override fun onRefresh() {
        Timber.i("refreshing....")

        activity_catalogue_refresh.postDelayed({
            cataloguePresenter.getCatalogue()
            cataloguePresenter.setView()
        }, 1500)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_basket, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_basket -> {
                val goToBasket = Intent(this@CatalogueActivity, BasketActivity::class.java)
                startActivity(goToBasket)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showCatalogue(bookList: List<Book>) {
        activity_catalogue_refresh.isRefreshing = false

        catalogueAdapter.addAll(bookList)
        catalogueAdapter.notifyDataSetChanged()
        activity_catalogue_empty_db.visibility = View.GONE
    }

    override fun showEmpty() {
        activity_catalogue_refresh!!.isRefreshing = false
        activity_catalogue_empty_db.visibility = View.VISIBLE
    }

    override fun onBookSelected(isbn: String) {
        val startBook = Intent(this@CatalogueActivity, BookActivity::class.java)
        startBook.putExtra(EXTRA_BOOK_ID, isbn)
        startActivity(startBook)
    }

    override fun notConnected() {
        NotConnectedAlertDialog(this@CatalogueActivity).show()
    }

    protected inner class NotConnectedAlertDialog internal constructor(context: Context) : AlertDialog(context) {

        init {
            setCancelable(false)

            setTitle(getString(R.string.no_internet_connection))
            setMessage(getString(R.string.message_not_connected))

            setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.ok)) { _, _ ->
                dismiss()
                onBackPressed()
            }
        }
    }
}
