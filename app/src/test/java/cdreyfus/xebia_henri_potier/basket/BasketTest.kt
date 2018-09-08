package cdreyfus.xebia_henri_potier.basket

import org.junit.Assert
import org.junit.Before
import org.junit.Test

import cdreyfus.xebia_henri_potier.basket.promotion.CommercialOffersResponse
import cdreyfus.xebia_henri_potier.basket.promotion.Minus
import cdreyfus.xebia_henri_potier.basket.promotion.Percentage
import cdreyfus.xebia_henri_potier.basket.promotion.Slice
import cdreyfus.xebia_henri_potier.book.Book

class BasketTest {

    private var basket: Basket? = null
    @Before
    fun setUp() {
        basket = Basket()
    }

    @Test
    fun getInstance() {
        Assert.assertEquals(0, basket!!.booksQuantitiesMap.size.toLong())
        Assert.assertEquals(0, basket!!.getRegularPrice(), 0.0)
    }

    @Test
    fun addBookToBasket() {
        val book = Book()
        book.price = 30
        basket!!.addBookToBasket(book)

        Assert.assertEquals(1, basket!!.booksQuantitiesMap[book].toInt().toLong())
    }

    @Test
    fun deleteBookFromBasket() {
        val book = Book()
        basket!!.addBookToBasket(book)

        Assert.assertTrue(basket!!.booksQuantitiesMap.containsKey(book))
        basket!!.deleteBookFromBasket(book)
        Assert.assertFalse(basket!!.booksQuantitiesMap.containsKey(book))
    }

    @Test
    fun editQuantityBook() {
        val book = Book()
        basket!!.addBookToBasket(book)
        basket!!.editQuantityBook(book, 4)

        Assert.assertEquals(4, basket!!.booksQuantitiesMap[book].toInt().toLong())
    }

    @Test
    fun getRegularPrice() {
        val book1 = Book()
        book1.price = 30
        val book2 = Book()
        book2.price = 35

        basket!!.addBookToBasket(book1)
        basket!!.addBookToBasket(book2)

        basket!!.editQuantityBook(book1, 3)
        Assert.assertEquals(125, basket!!.getRegularPrice(), 0.0f)
    }

    @Test
    fun applyBestCommercialOffer_1() {
        val commercialOffersResponse = CommercialOffersResponse()
        commercialOffersResponse.addOffer(Slice(12, 100))
        commercialOffersResponse.addOffer(Minus(15))
        commercialOffersResponse.addOffer(Percentage(5))

        val book1 = Book()
        book1.price = 30
        val book2 = Book()
        book2.price = 35

        basket!!.addBookToBasket(book1)
        basket!!.addBookToBasket(book2)
        Assert.assertEquals(50, basket!!.applyBestCommercialOffer(commercialOffersResponse, basket!!.getRegularPrice()), 0)
    }

    @Test
    fun applyBestCommercialOffer_2() {
        val commercialOffersResponse = CommercialOffersResponse()
        commercialOffersResponse.addOffer(Slice(12, 50))
        commercialOffersResponse.addOffer(Minus(10))
        commercialOffersResponse.addOffer(Percentage(5))

        val book1 = Book()
        book1.price = 30
        val book2 = Book()
        book2.price = 35

        basket!!.addBookToBasket(book1)
        basket!!.addBookToBasket(book2)
        Assert.assertEquals(53, basket!!.applyBestCommercialOffer(commercialOffersResponse, basket!!.getRegularPrice()), 0)

    }

    @Test
    fun applyBestCommercialOffer_3() {
        val commercialOffersResponse = CommercialOffersResponse()
        commercialOffersResponse.addOffer(Slice(12, 100))
        commercialOffersResponse.addOffer(Minus(15))
        commercialOffersResponse.addOffer(Percentage(50))

        val book1 = Book()
        book1.price = 30
        val book2 = Book()
        book2.price = 35

        basket!!.addBookToBasket(book1)
        basket!!.addBookToBasket(book2)
        Assert.assertEquals(32.5, basket!!.applyBestCommercialOffer(commercialOffersResponse, basket!!.getRegularPrice()), 0)
    }

    @Test
    fun getPromotionCode() {
        val book1 = Book()
        book1.isbn = "book1"
        val book2 = Book()
        book2.isbn = "book2"

        basket!!.addBookToBasket(book1)
        basket!!.addBookToBasket(book2)
        basket!!.editQuantityBook(book1, 2)

        Assert.assertEquals("book1,book1,book2", basket!!.getPromotionCode())
    }

    @Test
    fun isEmpty() {
    }

    @Test
    fun getBooksQuantitiesMap() {
    }
}