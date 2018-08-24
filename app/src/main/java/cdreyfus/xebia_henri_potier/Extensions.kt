package cdreyfus.xebia_henri_potier

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import cdreyfus.xebia_henri_potier.basket.Basket
import cdreyfus.xebia_henri_potier.book.Book
import cdreyfus.xebia_henri_potier.utils.Utils
import com.google.gson.Gson
import java.util.HashSet

internal fun View.toggleVisibility() {
    if (visibility == View.VISIBLE) {
        visibility = View.INVISIBLE
    } else {
        visibility = View.VISIBLE
    }
}

internal fun loadCatalogue(sharedPreferences: SharedPreferences): ArrayList<Book>{
    val bookSet = sharedPreferences.getStringSet(Utils.CATALOGUE, HashSet())
    return bookSet.mapTo(java.util.ArrayList()) { Gson().fromJson(it, Book::class.java) }
}

//internal fun loadBasket(context: Context): Basket {
//    val mPrefs: SharedPreferences = context.getSharedPreferences("HenriPotierSharedPrefs", Context.MODE_PRIVATE)
//    val basketSet = mPrefs.getStringSet(Utils.EXTRA_LIST_BOOKS, HashSet())
//    return null
//}