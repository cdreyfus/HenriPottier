package cdreyfus.xebia_henri_potier.book

import android.os.Parcel
import android.os.Parcelable

data class Book(var isbn: String, var title: String, var price: Int, var synopsis: ArrayList<String>, var cover: String, var order: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.createStringArrayList(),
            parcel.readString(),
            parcel.readInt())



    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.writeString(isbn)
        parcel?.writeString(title)
        parcel?.writeInt(price)


        for (entry in synopsis) {
            parcel?.writeString(entry)
        }

        parcel?.writeString(cover)
        parcel?.writeInt(order)
    }

    override fun describeContents(): Int {
        return 0
    }


//    override fun writeToParcel(p0: Parcel?, p1: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

//    constructor(parcel: Parcel) : this(
////            parcel.readString(),
////            parcel.readString(),
////            parcel.readInt(),
////            parcel.readString(),
////            parcel.readString())
//
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(isbn)
//        parcel.writeString(title)
//        parcel.writeInt(price)
////        parcel.writeString(synopsis)
//        parcel.writeString(cover)
//    }

    //    overide fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<Book> {
//        override fun createFromParcel(parcel: Parcel): Book {
//            return Book(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Book?> {
//            return arrayOfNulls(size)
//        }
//
    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }

}
