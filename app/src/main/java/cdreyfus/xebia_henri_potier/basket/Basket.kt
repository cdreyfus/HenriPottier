package cdreyfus.xebia_henri_potier.basket

import android.os.Parcel
import android.os.Parcelable
import cdreyfus.xebia_henri_potier.book.Book
import java.util.*

class Basket : Parcelable {


    var booksQuantitiesMap: HashMap<Book, Int>

    init {
        booksQuantitiesMap = hashMapOf()
    }

    constructor(booksQuantitiesMap: HashMap<Book, Int>) {
        this.booksQuantitiesMap = booksQuantitiesMap
    }

    constructor()

    protected constructor(`in`: Parcel) {
        booksQuantitiesMap = HashMap()
        val size = `in`.readInt()
        for (i in 0 until size) {
            val book = `in`.readValue(Book::class.java.classLoader) as Book
            val value = `in`.readInt()
            booksQuantitiesMap[book] = value
        }
    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(booksQuantitiesMap.size)
        for ((key, value) in booksQuantitiesMap) {
            dest.writeValue(key)
            dest.writeInt(value)
        }
    }

    companion object {

        val CREATOR: Parcelable.Creator<Basket> = object : Parcelable.Creator<Basket> {
            override fun createFromParcel(`in`: Parcel): Basket {
                return Basket(`in`)
            }

            override fun newArray(size: Int): Array<Basket?> {
                return arrayOfNulls(size)
            }
        }
    }
}