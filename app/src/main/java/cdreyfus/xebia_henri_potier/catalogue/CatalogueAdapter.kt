package cdreyfus.xebia_henri_potier.catalogue

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cdreyfus.xebia_henri_potier.R.layout.item_catalogue_view
import cdreyfus.xebia_henri_potier.book.Book
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_catalogue_view.view.*
import java.util.*


class CatalogueAdapter internal constructor(
        private val cataloguePresenter: CataloguePresenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val bookList: MutableList<Book>

    init {
        bookList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(item_catalogue_view, parent, false)
        return CatalogueHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val book = bookList[position]
        val catalogueHolder = holder as CatalogueHolder
        catalogueHolder.setUpItem(book)
        catalogueHolder.itemView
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    fun addAll(collection: List<Book>) {
        bookList.clear()
        bookList.addAll(collection)
    }

    internal inner class CatalogueHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemLabel: TextView = itemView.catalogue_item_label
        var imageView: ImageView = itemView.catalogue_item_image
        var itemPrice: TextView = itemView.catalogue_item_price

        fun setUpItem(book: Book) {
            Picasso.get().load(book.cover).into(imageView)
            itemLabel.text = book.title
            itemPrice.text = String.format("%s €", book.price)

            itemView.setOnClickListener { v -> cataloguePresenter.clickOnBook(book.isbn) }
        }
    }
}
