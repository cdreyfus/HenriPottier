package cdreyfus.xebia_henri_potier.basket

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cdreyfus.xebia_henri_potier.R
import cdreyfus.xebia_henri_potier.book.Book
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_basket_view.view.*
import java.util.*

class BasketRecyclerAdapter(private val basketPresenter: BasketPresenter?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val bookIntegerSortedMap: HashMap<Book, Int> = hashMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_basket_view, parent, false)

        return BasketItemHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val book = bookIntegerSortedMap.entries.toTypedArray()[position].key
        val quantity:Int = bookIntegerSortedMap.entries.toTypedArray()[position].value

        val basketItemHolder = holder as BasketItemHolder
        basketItemHolder.setItem(book, quantity)

        basketItemHolder.itemView.setOnClickListener {
            basketPresenter?.setNumberPicker(book)
        }
    }

    override fun getItemCount(): Int {
        return bookIntegerSortedMap.size
    }

    fun addAll(collection: HashMap<Book, Int>) {
        bookIntegerSortedMap.putAll(collection)
    }

    class BasketItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setItem(book: Book, quantity: Int) {
            Picasso.get().load(book.cover).into(itemView.basket_item_image)
            itemView.basket_item_label.text = book.title
            itemView.basket_item_price.text = String.format("%s €", book.price)
            itemView.basket_item_quantity.text = String.format("x %s", quantity)
        }

    }

}