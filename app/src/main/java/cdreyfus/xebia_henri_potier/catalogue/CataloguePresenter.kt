package cdreyfus.xebia_henri_potier.catalogue

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import cdreyfus.xebia_henri_potier.HenriPotierApplication
import cdreyfus.xebia_henri_potier.book.Book
import cdreyfus.xebia_henri_potier.book.IBookApi
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class CataloguePresenter(private val view: View, private val context: Context) {

    private var mPrefs: SharedPreferences = context.getSharedPreferences("HenriPotierSharedPrefs", Context.MODE_PRIVATE)

    private fun loadCatalogue(): java.util.ArrayList<Book> {
        val contactSet = mPrefs.getStringSet(HenriPotierApplication.CATALOGUE, HashSet())
        return contactSet.mapTo(java.util.ArrayList()) { Gson().fromJson(it, Book::class.java) }
    }

    private fun saveCatalogue(books: ArrayList<Book>) {
        val editor = mPrefs.edit()
        editor.clear()
        val bookSet = books
                .map { Gson().toJson(it) }
                .toSet()
        editor.putStringSet(HenriPotierApplication.CATALOGUE, bookSet)
        editor.apply()
    }


    @SuppressLint("CheckResult")
    fun getCatalogue() {

        val mBookApi: IBookApi = HenriPotierApplication.createBookApi()

        val catalogueRequest = mBookApi.books
        catalogueRequest.enqueue(object : Callback<ArrayList<Book>> {
            override fun onFailure(call: Call<ArrayList<Book>>, t: Throwable) {
                Timber.d(t.localizedMessage)
            }

            override fun onResponse(call: Call<ArrayList<Book>>, response: Response<ArrayList<Book>>) {
                val books: ArrayList<Book>? = response.body()
                if (books != null) {
                    saveCatalogue(books)
                }
            }
        })
    }

    fun setView() {
        var listBooks: ArrayList<Book> = loadCatalogue()
        if (!listBooks.isEmpty()) {
            view.showCatalogue(listBooks);
        } else {
            view.showEmpty();
        }
    }

    fun clickOnBook(isbn: String) {
        view.onBookSelected(isbn)
    }

    interface View {

        fun showCatalogue(bookList: List<Book>)

        fun showEmpty()

        fun onBookSelected(isbn: String)

        fun notConnected()

    }
}
