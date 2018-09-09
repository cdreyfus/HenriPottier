package cdreyfus.xebia_henri_potier.catalogue

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import cdreyfus.xebia_henri_potier.HenriPotierApplication
import cdreyfus.xebia_henri_potier.basket.Basket
import cdreyfus.xebia_henri_potier.book.Book
import cdreyfus.xebia_henri_potier.book.BookApi
import cdreyfus.xebia_henri_potier.utils.Utils
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.*


class CataloguePresenter {

    private var view: View? = null
    private var basket: Basket = Basket()
    private var sharedPrefs:SharedPreferences? = null

    fun attachView(view: View){
        this.view = view
    }

    fun detachView(){
        view = null
    }

    fun setSharedPrefs(sharedPrefs: SharedPreferences){
        this.sharedPrefs = sharedPrefs
    }

    private fun saveCatalogue(books: ArrayList<Book>) {
        val editor = sharedPrefs?.edit()
        editor?.clear()
        val bookSet = books
                .map { Gson().toJson(it) }
                .toHashSet()
        editor?.putStringSet(Utils.CATALOGUE, bookSet)
        editor?.apply()
    }


    @SuppressLint("CheckResult")
    fun getCatalogue() {

        val mBookApi: BookApi = HenriPotierApplication.createBookApi()
        val catalogueRequest = mBookApi.books
        catalogueRequest.enqueue(object : Callback<ArrayList<Book>> {
            override fun onFailure(call: Call<ArrayList<Book>>, t: Throwable) {
                Timber.d(t.localizedMessage)
                generateBooks()
            }

            override fun onResponse(call: Call<ArrayList<Book>>, response: Response<ArrayList<Book>>) {
                val books: ArrayList<Book> = response.body() as ArrayList<Book>
                for (book in books) {
                    book.order = books.indexOf(book) + 1
                }
                saveCatalogue(books)
            }
        })
    }

    private fun generateBooks() {
        val booksString = view?.readContactJsonFile()
        val list: ArrayList<Book> = arrayListOf()
        try {
            val booksJson = JSONArray(booksString)
            for (i in 0 until booksJson.length()) {
                val bookJson = booksJson.getJSONObject(i)
                val bookString = bookJson.toString()

                val book: Book = Gson().fromJson(bookString, Book::class.java)
                book.order = i + 1
                list.add(book)
            }
            saveCatalogue(list)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    fun setCatalogueView() {
        val listBooks: List<Book> = loadCatalogue()!!.sortedBy { it.order }
        if (!listBooks.isEmpty()) {
            view?.showCatalogue(listBooks)
        } else {
            view?.showEmpty()
        }
    }

    private fun loadCatalogue(): ArrayList<Book>?{
        val bookSet = sharedPrefs?.getStringSet(Utils.CATALOGUE, HashSet())
        return bookSet?.mapTo(java.util.ArrayList()) { Gson().fromJson(it, Book::class.java) }
    }

    fun clickOnBook(book: Book) {
        view?.onBookSelected(book)
    }

    fun sendBasket(intent: Intent) {
        intent.putExtra(Utils.EXTRA_CONTENT_BASKET, basket)
    }

    fun setBasket(basket: Basket) {
        this.basket = basket
    }

    interface View {

        fun showCatalogue(bookList: List<Book>)

        fun showEmpty()

        fun onBookSelected(book: Book)

        fun notConnected()

        fun readContactJsonFile(): String?

    }
}
