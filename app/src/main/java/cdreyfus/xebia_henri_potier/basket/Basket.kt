package cdreyfus.xebia_henri_potier.basket

import android.os.Parcelable
import cdreyfus.xebia_henri_potier.book.Book
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class Basket(var booksQuantitiesMap: HashMap<Book, Int>) : Parcelable {
    constructor() : this(hashMapOf())

}