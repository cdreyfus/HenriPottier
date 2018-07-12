package cdreyfus.xebia_henri_potier.book;

import java.util.List;

import cdreyfus.xebia_henri_potier.book.Book;
import cdreyfus.xebia_henri_potier.basket.promotion.CommercialOffersResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IBookInterface {

    @GET("books")
    Observable<List<Book>> getBooks();

    @GET("books/{list_books}/commercialOffers")
    Observable<CommercialOffersResponse> getCommercialOffer(@Path("list_books") String list_books);

}
