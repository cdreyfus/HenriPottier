package cdreyfus.xebia_henri_potier.book

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cdreyfus.xebia_henri_potier.HenriPotierApplication.Companion.setHttpClient
import cdreyfus.xebia_henri_potier.R
import cdreyfus.xebia_henri_potier.basket.BasketActivity
import cdreyfus.xebia_henri_potier.utils.Utils
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book.*
import java.util.*

class BookActivity : AppCompatActivity(), BookPresenter.View {

    private var bookPresenter: BookPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.bookPresenter = BookPresenter(this, getSharedPreferences(Utils.SHARED_PREFS, Context.MODE_PRIVATE))

        setContentView(R.layout.activity_book)

        if (intent.hasExtra(Utils.EXTRA_BOOK_ID)) {
            bookPresenter?.initBook(intent.getStringExtra(Utils.EXTRA_BOOK_ID))
        }
    }

    override fun onResume() {
        super.onResume()
        bookPresenter?.getBook()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_basket, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_basket -> {
                val goToBasket = Intent(this@BookActivity, BasketActivity::class.java)
//                if (!mBasket.booksQuantitiesMap.isEmpty()) {
//                    goToBasket.putExtra(Utils.EXTRA_LIST_BOOKS, mBasket)
//                }
                startActivity(goToBasket)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setTitre(titre: String?) {
        Objects.requireNonNull<ActionBar>(supportActionBar).title = titre
    }

    override fun setCover(cover: String?) {
        val picasso: Picasso? = Picasso.Builder(this).downloader(OkHttp3Downloader(setHttpClient())).build()
        picasso?.load(cover)?.into(activity_book_cover)
    }

    override fun setResume(resume: ArrayList<String>?) {
        val synopsis = StringBuilder()

        if (resume != null) {
            for (part in resume) {
                synopsis.append(part).append("\n")
            }
        }
        val fomatedResume = synopsis.substring(0, synopsis.lastIndexOf("\n"))
        activity_book_synopsis.text = fomatedResume
    }

    override fun setPrice(price: Float?) {
        activity_book_price.text = String.format("%s €", price)
    }

    override fun setBookInBasket(quantity: Int?) {
        activity_book_add_to_basket.visibility = View.GONE
        activity_book_edit_quantity_button.visibility = View.VISIBLE
        activity_book_remove_from_basket_button.visibility = View.VISIBLE

        activity_book_edit_quantity_button.text = String.format("%s (%s)", getString(R.string.edit_quantity), quantity)
    }

    override fun setNotInBasket() {
        activity_book_add_to_basket.visibility = View.VISIBLE
        activity_book_edit_quantity_button.visibility = View.GONE
        activity_book_remove_from_basket_button.visibility = View.GONE
    }

//    override fun showNumberPicker(title: String?, quantity: Int) {
//        mNumberPickerDialog = NumberPickerDialog(this@BookActivity,
//                title,
//                { picker, oldVal, newVal ->
//                    bookPresenter?.editQuantityinBasket(newVal)
//
//                }, 10,
//                1,
//                quantity)
//
//        mNumberPickerDialog?.show()
//    }

//    override fun hideNumberPicker() {
//        mNumberPickerDialog!!.hide()
//    }

//    @OnClick(R.id.activity_book_add_to_basket)
//    internal fun clickAddToBasket() {
//        bookPresenter?.addToBasket()
//    }
//
//    @OnClick(R.id.activity_book_remove_from_basket_button)
//    internal fun clickRemoveFromBasket() {
//        bookPresenter?.removeFromBasket()
//    }
//
//    @OnClick(R.id.activity_book_edit_quantity_button)
//    internal fun clickEditQuantity() {
//        bookPresenter?.selectQuantityInBasket()
//    }


}
