package cdreyfus.xebia_henri_potier.book

import android.content.SharedPreferences
import cdreyfus.xebia_henri_potier.basket.Basket
import cdreyfus.xebia_henri_potier.loadCatalogue

class BookPresenter internal constructor(private val view: View, private val sharedPreferences: SharedPreferences) {
    private var book: Book? = null
    private lateinit var basket: Basket

    fun initBook(isbn: String) {
        val catalogue: ArrayList<Book> = loadCatalogue(sharedPreferences)
        for(item in catalogue){
            if(item.isbn.equals(isbn)){
                book = item
            }
        }
    }

    fun initBasket(mBasket: Basket) {
        this.basket = mBasket
    }


    private fun setTitre(titre: String?) {
        view.setTitre(titre)
    }

    private fun setCover(cover: String?) {
        view.setCover(cover)
    }

    private fun setSynopsis(resume: ArrayList<String>?) {
        view.setResume(resume)
    }

    private fun setPrice(price: Float?) {
        view.setPrice(price)
    }

    fun getBook() {
        updateBook()
        updateBasketButtons()
    }

    private fun updateBook() {
        setTitre(book?.title)
        setCover(book?.cover)
        setSynopsis(book?.synopsis)
        setPrice(book?.price?.toFloat())
    }

    private fun updateBasketButtons() {
        if (basket.booksQuantitiesMap.containsKey(book)) {
//            view.setBookInBasket(basket.booksQuantitiesMap[book])
        } else {
            view.setNotInBasket()
        }
    }

    fun addToBasket() {
        basket.booksQuantitiesMap[book] = 1
//        view.setBookInBasket(basket.booksQuantitiesMap[book])
    }

    fun removeFromBasket() {
        basket!!.booksQuantitiesMap.remove(book)
        view.setNotInBasket()
    }

    fun editQuantityinBasket(newVal: Int) {
        for (entry in basket.booksQuantitiesMap.entries) {
            if (entry.key == book) {
                entry.setValue(newVal)
            }
        }
        view.setBookInBasket(newVal)
        view.hideNumberPicker()
    }

    fun selectQuantityInBasket() {
//        view.showNumberPicker(book.title, basket.booksQuantitiesMap[book])
    }


    interface View {

        fun setTitre(titre: String?)

        fun setCover(cover: String?)

        fun setResume(resume: ArrayList<String>?)

        fun setPrice(price: Float?)

        fun setBookInBasket(quantity: Int)

        fun setNotInBasket()

        fun showNumberPicker(title: String, quantity: Int)

        fun hideNumberPicker()
    }

}


