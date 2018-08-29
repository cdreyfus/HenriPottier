package cdreyfus.xebia_henri_potier

import android.content.SharedPreferences
import android.view.View
import cdreyfus.xebia_henri_potier.book.Book
import cdreyfus.xebia_henri_potier.utils.Utils
import com.google.gson.Gson
import java.util.*


internal fun loadCatalogue(sharedPreferences: SharedPreferences): ArrayList<Book>{
    val bookSet = sharedPreferences.getStringSet(Utils.CATALOGUE, HashSet())
    return bookSet.mapTo(java.util.ArrayList()) { Gson().fromJson(it, Book::class.java) }
}

//
//internal fun loadBasket(sharedPreferences: SharedPreferences): Basket {
//    val basket:  Basket = Basket(hashMapOf())
//    try {
//        if (sharedPreferences != null) {
//            val jsonString = sharedPreferences!!.getString(Utils.EXTRA_LIST_BOOKS, JSONObject().toString())
//            val jsonObject = JSONObject(jsonString)
//            val keysItr = jsonObject.keys()
//            while (keysItr.hasNext()) {
//                val key = keysItr.next()
//                val value = jsonObject.get(key) as Boolean
//                basket[key] = value
//            }
//        }
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//
//    return outputMap
//}