package cdreyfus.xebia_henri_potier.interfaces;

import android.database.Observable;

import java.util.List;

import cdreyfus.xebia_henri_potier.models.Book;
import cdreyfus.xebia_henri_potier.models.CommercialOffer;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookInterface {

    @GET("books")
    Observable<List<Book>> getBooks();

    @GET("books/{list_books}/commercialOffer")
    CommercialOffer getCommercialOffer(@Path("list_books") String list_books);

}
