package cdreyfus.xebia_henri_potier.book

import android.os.Parcel
import android.os.Parcelable

data class Book(var isbn: String, var title: String, var price: Int, var synopsis: ArrayList<String>, var cover: String, var order: Int) : Parcelable {
    internal constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.createStringArrayList(),
            parcel.readString(),
            parcel.readInt())

    constructor() : this(
            "",
            "",
            0,
            ArrayList(),
            "",
            0
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(isbn)
        parcel.writeString(title)
        parcel.writeInt(price)
        parcel.writeStringList(synopsis)
        parcel.writeString(cover)
        parcel.writeInt(order)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }

}