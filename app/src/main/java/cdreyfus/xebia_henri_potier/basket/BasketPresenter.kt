package cdreyfus.xebia_henri_potier.basket

import android.content.Intent
import cdreyfus.xebia_henri_potier.HenriPotierApplication
import cdreyfus.xebia_henri_potier.basket.promotion.CommercialOffersList
import cdreyfus.xebia_henri_potier.book.Book
import cdreyfus.xebia_henri_potier.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.Comparator

class BasketPresenter {

    private var view:View? = null
    private var basket: Basket = Basket()

    fun attachView(view:View){
        this.view = view
    }

    fun detachView(){
        this.view = null
    }

    fun setBasket(basket: Basket){
        this.basket = basket
    }

    fun generatePromoCode(map: HashMap<Book, Int>): String {
        var promotionCode = StringBuilder()
        if (map.isNotEmpty()) {
            for ((key, value) in map) {
                for (i in 0 until value) {
                    promotionCode.append(String.format("%s,", key.isbn))
                }
            }
            promotionCode = promotionCode.deleteCharAt(promotionCode.length - 1)
        }
        return promotionCode.toString()
    }

    fun calculateRegularPrice(map: HashMap<Book, Int>): Float {
        var regularPrice = 0f
        for ((key, value) in map) {
            regularPrice += (key.price * value).toFloat()
        }
        return regularPrice
    }

    fun setPrices() {

        val regularPrice = calculateRegularPrice(basket.booksQuantitiesMap)
        val promoCode = generatePromoCode(basket.booksQuantitiesMap)

        view?.setRegularPrice(regularPrice)

        if (basket.booksQuantitiesMap.isNotEmpty()) {

            val commercialOfferApi = HenriPotierApplication.createCommercialOffferApi()

            val commercialOfferRequest = commercialOfferApi.getCommercialOffer(promoCode)


            commercialOfferRequest.enqueue(object : Callback<CommercialOffersList> {
                override fun onFailure(call: Call<CommercialOffersList>, t: Throwable) {
                    Timber.d(t.localizedMessage)
                }

                override fun onResponse(call: Call<CommercialOffersList>, response: Response<CommercialOffersList>) {
                    val commercialOffers: CommercialOffersList? = response.body()
                    val finalPrice = applyBestCommercialOffer(commercialOffers, regularPrice)
                    println(finalPrice)
                    view?.setFinalPrice(finalPrice)
                    view?.setPromoValue(finalPrice - regularPrice)
                }
            })
        } else {
            view?.setFinalPrice(regularPrice)
            view?.setPromoValue(0f)
        }
    }

    fun updateBasketContent() {
        if (basket.booksQuantitiesMap.isEmpty()) {
            view?.showEmpty()
        } else {
            view?.showBooks(basket.booksQuantitiesMap)
        }
    }

    fun applyBestCommercialOffer(commercialOffers: CommercialOffersList?, regularPrice: Float): Float {
        var minimumValue = regularPrice
        if (commercialOffers != null) {
            for (commercialOffer in commercialOffers.offers){
                minimumValue = Math.min(minimumValue, commercialOffer.applyOffer(regularPrice))
            }
        }
        return minimumValue
    }

    fun setBookInBasket(book: Book, quantity: Int) {
        basket.booksQuantitiesMap[book] = quantity
    }


    fun setNumberPicker(book: Book) {
        view?.showNumberPicker(book, basket.booksQuantitiesMap.getValue(book))
    }

    fun sendBasketForResult(intent: Intent) {
        intent.putExtra(Utils.EXTRA_CONTENT_BASKET, basket)
    }

    interface View {

        fun setRegularPrice(regularPrice: Float)

        fun setFinalPrice(finalPrice: Float)

        fun setPromoValue(promoValue: Float)

        fun showBooks(listBooks: HashMap<Book, Int>)

        fun showEmpty()

        fun showNumberPicker(book: Book, quantity: Int)

        fun hideNumberPicker()

    }

}
