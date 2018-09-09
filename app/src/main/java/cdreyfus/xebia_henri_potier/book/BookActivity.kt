package cdreyfus.xebia_henri_potier.book

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.NumberPicker
import cdreyfus.xebia_henri_potier.HenriPotierApplication.Companion.setHttpClient
import cdreyfus.xebia_henri_potier.R
import cdreyfus.xebia_henri_potier.basket.Basket
import cdreyfus.xebia_henri_potier.basket.BasketActivity
import cdreyfus.xebia_henri_potier.utils.Utils
import cdreyfus.xebia_henri_potier.utils.Utils.BASKET_CONTENT
import cdreyfus.xebia_henri_potier.utils.Utils.EXTRA_CONTENT_BASKET
import com.cuiweiyou.numberpickerdialog.NumberPickerDialog
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book.*
import java.util.*

class BookActivity : AppCompatActivity(), BookPresenter.View {

    private var bookPresenter: BookPresenter = BookPresenter()
    private var mNumberPickerDialog: NumberPickerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        bookPresenter.attachView(this)

        if (intent.hasExtra(Utils.EXTRA_BOOK)) {
            bookPresenter.initBook(intent.getParcelableExtra(Utils.EXTRA_BOOK))
        }

        if(intent.hasExtra(EXTRA_CONTENT_BASKET)){
            val basket: Basket = intent.getParcelableExtra(EXTRA_CONTENT_BASKET)
            bookPresenter.initBasket(basket)
        }
    }

    override fun onResume() {
        super.onResume()
        bookPresenter.updateViewBook()
        bookPresenter.updateBasketButtons()
    }

    override fun onDestroy() {
        bookPresenter.detachView()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_basket, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_basket -> {
                val goToBasket = Intent(this@BookActivity, BasketActivity::class.java)
                bookPresenter.sendBasketForResult(goToBasket)
                startActivityForResult(goToBasket, BASKET_CONTENT)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setTitre(titre: String) {
        Objects.requireNonNull<ActionBar>(supportActionBar).title = titre
    }

    override fun setCover(cover: String) {
        val picasso: Picasso? = Picasso.Builder(this).downloader(OkHttp3Downloader(setHttpClient())).build()
        picasso?.load(cover)?.into(activity_book_cover)
    }

    override fun setResume(resume: ArrayList<String>) {
        val synopsis = StringBuilder()

        for (part in resume) {
            synopsis.append(part).append("\n")
        }
        val fomatedResume = synopsis.substring(0, synopsis.lastIndexOf("\n"))
        activity_book_synopsis.text = fomatedResume
    }

    override fun setPrice(price: Float?) {
        activity_book_price.text = String.format("%s €", price)
    }

    override fun setBookInBasket(quantity: Int) {
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

    override fun showNumberPicker(title: String, quantity: Int) {

        mNumberPickerDialog = NumberPickerDialog(
                this,
                title,
                fun(_: NumberPicker, _: Int, newVal: Int) {
                    bookPresenter.editQuantityinBasket(newVal)
                },
                10,
                1,
                quantity
        )
        mNumberPickerDialog?.show()
    }

    override fun hideNumberPicker() {
        mNumberPickerDialog?.hide()
    }

    fun clickAddToBasket(view: View) {
        bookPresenter.addToBasket()
    }

    fun clickRemoveFromBasket(view: View) {
        bookPresenter.removeFromBasket()
    }

    fun clickEditQuantity(view: View) {
        bookPresenter.selectQuantityInBasket()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == BASKET_CONTENT) {
            val basket: Basket = data?.getParcelableExtra(EXTRA_CONTENT_BASKET) as Basket
            bookPresenter.initBasket(basket)
        }
    }

    override fun onBackPressed() {
        val returnIntent = Intent()
        bookPresenter.sendBasketForResult(returnIntent)
        returnIntent.flags = Intent.FLAG_ACTIVITY_FORWARD_RESULT
        setResult(BASKET_CONTENT, returnIntent)
        finish()
    }


}
