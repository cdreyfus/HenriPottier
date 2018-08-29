package cdreyfus.xebia_henri_potier.basket

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.NumberPicker
import cdreyfus.xebia_henri_potier.R
import cdreyfus.xebia_henri_potier.book.Book
import cdreyfus.xebia_henri_potier.utils.Utils
import com.cuiweiyou.numberpickerdialog.NumberPickerDialog
import kotlinx.android.synthetic.main.activity_basket.*
import java.util.*

class BasketActivity : AppCompatActivity(), BasketPresenter.View {

    private var basketPresenter: BasketPresenter? = null
    private var basketAdapter: BasketRecyclerAdapter? = null
    private var numberPickerDialog: NumberPickerDialog? = null


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)
        supportActionBar?.setTitle(R.string.basket)
        basketPresenter = BasketPresenter(this)

        if (intent.hasExtra(Utils.EXTRA_LIST_BOOKS)) {
            //Basket basket = getIntent().getParcelableExtra(EXTRA_BOOK_ID);
            val basket = intent.extras.get(Utils.EXTRA_LIST_BOOKS) as Basket

            basketPresenter?.setBasket(basket)
        }

        basketAdapter = BasketRecyclerAdapter(basketPresenter)
        initRecycler()

    }

    override fun onResume() {
        super.onResume()
        basketPresenter?.updateBasketContent()
        basketPresenter?.setPrices()
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

    override fun showNumberPicker(title: String?, quantity: Int?) {

        numberPickerDialog = NumberPickerDialog(
                this,
                title,
                fun(numberPicker: NumberPicker, newVal:Int, oldVal: Int){
                    basketPresenter?.editQuantityBook(newVal)
                    basketPresenter?.updateBasketContent()
                    basketPresenter?.setPrices()
                },
                10,
                1,
                quantity!!
                )

        numberPickerDialog?.show()
    }


    override fun hideNumberPicker() {
        numberPickerDialog?.hide()
    }
}

