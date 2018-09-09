package cdreyfus.xebia_henri_potier.basket

import cdreyfus.xebia_henri_potier.basket.promotion.CommercialOffersList
import cdreyfus.xebia_henri_potier.basket.promotion.Minus
import cdreyfus.xebia_henri_potier.basket.promotion.Percentage
import cdreyfus.xebia_henri_potier.basket.promotion.Slice
import cdreyfus.xebia_henri_potier.book.Book
import org.junit.Assert.assertEquals
import org.junit.Test

class BasketPresenterTest {

    private val basketPresenter:BasketPresenter = BasketPresenter()

    @Test
    fun generate_promo_code_basketIsEmpty() {
        val basket = Basket()
        val promoCode:String = basketPresenter.generatePromoCode(basket.booksQuantitiesMap)
        assert(promoCode.isEmpty())
    }

    @Test
    fun generate_promo_code_basketNotEmpty() {
        val book1 = Book()
        book1.isbn = "book1"
        val book2 = Book()
        book2.isbn = "book2"

        val basket = Basket(hashMapOf(book1 to 2, book2 to 1))
        val promoCode:String = basketPresenter.generatePromoCode(basket.booksQuantitiesMap)

        assert(promoCode.contains("book1,book1"))
        assert(containsOnce(promoCode, "book2"))
        assertEquals(3, promoCode.split(",").size)
    }

    @Test
    fun calculate_regular_price_basketIsEmpty() {
        val basket = Basket()
        assertEquals(0.0f, basketPresenter.calculateRegularPrice(basket.booksQuantitiesMap))
    }

    @Test
    fun calculate_regular_price_basketNotEmpty() {
        val book1 = Book()
        book1.price = 30
        val book2 = Book()
        book2.price = 35

        val basket = Basket(hashMapOf(book1 to 3, book2 to 1))
        assertEquals(125.0f, basketPresenter.calculateRegularPrice(basket.booksQuantitiesMap))
    }

    @Test
    fun apply_best_commercial_offer_shouldApplyMinus_basketNotEmpty(){
        val commercialOffersResponse = CommercialOffersList(arrayListOf(
                Slice(12,100),
                Minus(15),
                Percentage(5)))

        val book1 = Book()
        book1.price = 30
        val book2 = Book()
        book2.price = 35

        val basket = Basket(hashMapOf(book1 to 1, book2 to 1))
        val regularPrice = basketPresenter.calculateRegularPrice(basket.booksQuantitiesMap)
        val newPrice = basketPresenter.applyBestCommercialOffer(commercialOffersResponse, regularPrice)

        assertEquals(50.0f, newPrice)
    }

    @Test
    fun apply_best_commercial_offer_shouldApplySlice_basketNotEmpty(){
        val commercialOffersResponse = CommercialOffersList(arrayListOf(
                Slice(12,50),
                Minus(10),
                Percentage(5)))

        val book1 = Book()
        book1.price = 30
        val book2 = Book()
        book2.price = 35

        val basket = Basket(hashMapOf(book1 to 1, book2 to 1))
        val regularPrice = basketPresenter.calculateRegularPrice(basket.booksQuantitiesMap)
        val newPrice = basketPresenter.applyBestCommercialOffer(commercialOffersResponse, regularPrice)

        assertEquals(53.0f, newPrice)
    }

    @Test
    fun apply_best_commercial_offer_shouldApplyPercentage_basketNotEmpty(){
        val commercialOffersResponse = CommercialOffersList(arrayListOf(
                Slice(12,100),
                Minus(15),
                Percentage(50)))

        val book1 = Book()
        book1.price = 30
        val book2 = Book()
        book2.price = 35

        val basket = Basket(hashMapOf(book1 to 1, book2 to 1))
        val regularPrice = basketPresenter.calculateRegularPrice(basket.booksQuantitiesMap)
        val newPrice = basketPresenter.applyBestCommercialOffer(commercialOffersResponse, regularPrice)

        assertEquals(32.5f, newPrice)
    }


    private fun containsOnce(s: String, substring: CharSequence): Boolean {
        val substring0 = substring.toString()
        val i = s.indexOf(substring0)
        return i != -1 && i == s.lastIndexOf(substring0)
    }
}