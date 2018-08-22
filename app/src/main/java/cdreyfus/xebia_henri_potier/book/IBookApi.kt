package cdreyfus.xebia_henri_potier.book

import cdreyfus.xebia_henri_potier.basket.promotion.CommercialOffersResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IBookApi {

    @get:GET("books")
    val books: Call<ArrayList<Book>>

    @GET("books/{list_books}/commercialOffers")
    fun getCommercialOffer(@Path("list_books") list_books: String): Observable<CommercialOffersResponse>

}
