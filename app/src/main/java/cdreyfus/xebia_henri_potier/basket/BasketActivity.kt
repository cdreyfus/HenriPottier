package cdreyfus.xebia_henri_potier.basket

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.NumberPicker
import cdreyfus.xebia_henri_potier.R
import cdreyfus.xebia_henri_potier.book.Book
import cdreyfus.xebia_henri_potier.utils.Utils.BASKET_CONTENT
import cdreyfus.xebia_henri_potier.utils.Utils.EXTRA_CONTENT_BASKET
import com.cuiweiyou.numberpickerdialog.NumberPickerDialog
import kotlinx.android.synthetic.main.activity_basket.*
import java.util.*
import kotlin.collections.HashMap


class BasketActivity : AppCompatActivity(), BasketPresenter.View {

    private var basketPresenter: BasketPresenter = BasketPresenter()
    private var basketAdapter: BasketRecyclerAdapter? = null
    private var numberPickerDialog: NumberPickerDialog? = null


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        supportActionBar?.setTitle(R.string.basket)
        basketPresenter.attachView(this)

        if(intent.hasExtra(EXTRA_CONTENT_BASKET)){
           val basket = intent.extras?.getParcelable(EXTRA_CONTENT_BASKET) as Basket
            basketPresenter.setBasket(basket)
        }

        basketAdapter = BasketRecyclerAdapter(basketPresenter)
        initRecycler()
    }

    override fun onResume() {
        super.onResume()
        basketPresenter.updateBasketContent()
        basketPresenter.setPrices()
    }

    override fun onDestroy() {
        basketPresenter.detachView()
        super.onDestroy()
    }

    private fun initRecycler() {
        activity_basket_list.layoutManager = LinearLayoutManager(this)
        activity_basket_list.adapter = basketAdapter
    }

    override fun setRegularPrice(regularPrice: Float) {
        activity_basket_regular_price.text = String.format(Locale.ENGLISH, "Total: %.2f €", regularPrice)
    }

    override fun setPromoValue(promoValue: Float) {
        activity_basket_promo.text = String.format(Locale.ENGLISH, "Promotion: %.2f €", promoValue)
    }

    override fun setFinalPrice(finalPrice: Float) {
        activity_basket_final_price.text = String.format(Locale.ENGLISH, "New Total: %.2f €", finalPrice)
    }


    override fun showBooks(listBooks: HashMap<Book, Int>) {
        basketAdapter?.addAll(listBooks)
        basketAdapter?.notifyDataSetChanged()

        activity_basket_empty.visibility = View.GONE
        activity_basket_list.visibility = View.VISIBLE
    }

    override fun showEmpty() {
        activity_basket_empty.visibility = View.VISIBLE
        activity_basket_list.visibility = View.GONE
    }

    override fun showNumberPicker(book: Book, quantity: Int) {

        numberPickerDialog = NumberPickerDialog(
                this,
                book.title,
                fun(_: NumberPicker, _:Int, newVal: Int){
                    basketPresenter.setBookInBasket(book, newVal)
                    basketPresenter.updateBasketContent()
                    basketPresenter.setPrices()
                },
                10,
                1,
                quantity
                )

        numberPickerDialog?.show()
    }


    override fun hideNumberPicker() {
        numberPickerDialog?.hide()
    }

    override fun onBackPressed() {
        val returnIntent = Intent()
        returnIntent.flags = Intent.FLAG_ACTIVITY_FORWARD_RESULT
        basketPresenter.sendBasketForResult(returnIntent)
        setResult(BASKET_CONTENT, returnIntent)
        finish()
    }
}

