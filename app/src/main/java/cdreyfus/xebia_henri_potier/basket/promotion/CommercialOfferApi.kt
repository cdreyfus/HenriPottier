package cdreyfus.xebia_henri_potier.basket.promotion

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CommercialOfferApi {


    @GET("books/{list_books}/commercialOffers")
    fun getCommercialOffer(@Path("list_books") list_books: String): Call<CommercialOffersResponse>
}
