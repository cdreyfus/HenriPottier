package cdreyfus.xebia_henri_potier.basket.promotion;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ICommercialOfferApi {


    @GET("books/{list_books}/commercialOffers")
    Single<CommercialOffersResponse> getCommercialOffer(@Path("list_books") String list_books);
}
