package cdreyfus.xebia_henri_potier.basket

import android.annotation.SuppressLint
import cdreyfus.xebia_henri_potier.HenriPotierApplication
import cdreyfus.xebia_henri_potier.basket.promotion.CommercialOffersResponse
import cdreyfus.xebia_henri_potier.book.Book
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.*

class BasketPresenter(private val view: View) {

    private var basket: Basket = Basket()
    private var book: Book? = null


    fun setBasket(basket: Basket) {
        this.basket = basket
    }

    private fun generatePromoCode(map: HashMap<Book, Int>): String {
        var promotionCode = StringBuilder()
        if(map.isNotEmpty()) {
            for ((key, value) in map) {
                for (i in 0 until value) {
                    promotionCode.append(String.format("%s,", key.isbn))
                }
            }
            promotionCode = promotionCode.deleteCharAt(promotionCode.length - 1)
        }
        return promotionCode.toString()
    }

    private fun calculateRegularPrice(map: HashMap<Book, Int>): Float {
        var regularPrice = 0f
        for ((key, value) in map) {
            regularPrice += (key.price * value).toFloat()
        }
        return regularPrice
    }

    @SuppressLint("CheckResult")
    fun setPrices() {

        val regularPrice = calculateRegularPrice(basket.booksQuantitiesMap)
        val promoCode = generatePromoCode(basket.booksQuantitiesMap)

        view.setRegularPrice(regularPrice)

        if (basket.booksQuantitiesMap.isNotEmpty()) {

            val commercialOfferApi = HenriPotierApplication.createCommercialOffferApi()

            val commercialOfferRequest = commercialOfferApi.getCommercialOffer(promoCode)


            commercialOfferRequest.enqueue(object : Callback<CommercialOffersResponse> {
                override fun onFailure(call: Call<CommercialOffersResponse>, t: Throwable) {
                    Timber.d(t.localizedMessage)
                }

                override fun onResponse(call: Call<CommercialOffersResponse>, response: Response<CommercialOffersResponse>) {
                    val commercialOffersResponse: CommercialOffersResponse? = response.body()
                    val finalPrice = applyBestCommercialOffer(commercialOffersResponse, regularPrice)
                    view.setFinalPrice(finalPrice)
                    view.setPromoValue(finalPrice - regularPrice)
                }
            })
        } else {
            view.setFinalPrice(regularPrice)
            view.setPromoValue(0f)
        }
    }

    fun updateBasketContent() {
        if (basket.booksQuantitiesMap.isEmpty()) {
            view.showEmpty()
        } else {
            view.showBooks(basket.booksQuantitiesMap)
        }
    }

    fun initBook(book: Book) {
        this.book = book
    }

    fun setNumberPicker() {
        view.showNumberPicker(book?.title, basket.booksQuantitiesMap[book])
    }


    fun editQuantityBook(quantity: Int) {
        for (entry in basket.booksQuantitiesMap.entries) {
            if (entry.key == book) {
                entry.setValue(quantity)
            }
        }
    }

    fun applyBestCommercialOffer(commercialOffersResponse: CommercialOffersResponse?, regularPrice: Float): Float {
        var minimumValue = regularPrice
        if(commercialOffersResponse != null) {
            for (commercialOffer in commercialOffersResponse.getCommercialOffers()) {
                minimumValue = Math.min(minimumValue, commercialOffer.applyOffer(regularPrice))
            }
        }
        return minimumValue
    }

    interface View {

        fun setRegularPrice(regularPrice: Float)

        fun setFinalPrice(finalPrice: Float)

        fun setPromoValue(promoValue: Float)

        fun showBooks(listBooks: HashMap<Book, Int>)

        fun showEmpty()

        fun showNumberPicker(title: String?, quantity: Int?)

        fun hideNumberPicker()

    }

}
