package cdreyfus.xebia_henri_potier.book

import android.content.Intent
import cdreyfus.xebia_henri_potier.basket.Basket
import cdreyfus.xebia_henri_potier.utils.Utils

class BookPresenter internal constructor() {
    private var basket: Basket = Basket()
    private var view: View? = null
    private var book:Book = Book()

    private var quantity: Int = 0

    fun attachView(view:View){
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    fun initBook(book: Book){
        this.book = book
    }

    fun initBasket(basket: Basket) {
        this.basket = basket
        if (basket.booksQuantitiesMap.containsKey(book)) {
            quantity = basket.booksQuantitiesMap.getValue(book)
            updateBasketButtons()
        }
    }

    private fun setTitre(titre: String) {
        view?.setTitre(titre)
    }

    private fun setCover(cover: String) {
        view?.setCover(cover)
    }

    private fun setSynopsis(resume: ArrayList<String>) {
        view?.setResume(resume)
    }

    private fun setPrice(price: Float?) {
        view?.setPrice(price)
    }


    fun updateViewBook() {
        setTitre(book.title)
        setCover(book.cover)
        setSynopsis(book.synopsis)
        setPrice(book.price.toFloat())
    }

    fun updateBasketButtons() {
        if (quantity > 0) {
            view?.setBookInBasket(quantity)
        } else {
            view?.setNotInBasket()
        }
    }

    fun addToBasket() {
        quantity = 1
        basket.booksQuantitiesMap[book] = 1
        view?.setBookInBasket(quantity)

    }

    fun removeFromBasket() {
        quantity = 0
        basket.booksQuantitiesMap.remove(book)
        view?.setNotInBasket()
    }

    fun editQuantityinBasket(newVal: Int) {
        quantity = newVal
        basket.booksQuantitiesMap[book] = quantity
        view?.setBookInBasket(newVal)
        view?.hideNumberPicker()
    }

    fun selectQuantityInBasket() {
        view?.showNumberPicker(book.title, quantity)
    }

    fun sendBasketForResult(intent: Intent) {
        intent.putExtra(Utils.EXTRA_CONTENT_BASKET, basket)
    }


    interface View {

        fun setTitre(titre: String)

        fun setCover(cover: String)

        fun setResume(resume: ArrayList<String>)

        fun setPrice(price: Float?)

        fun setBookInBasket(quantity: Int)

        fun setNotInBasket()

        fun hideNumberPicker()

        fun showNumberPicker(title: String, quantity: Int)
    }

}


