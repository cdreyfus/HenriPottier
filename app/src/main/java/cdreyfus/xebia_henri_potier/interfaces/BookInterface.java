package cdreyfus.xebia_henri_potier.interfaces;

import android.database.Observable;

import java.util.ArrayList;
import java.util.List;

import cdreyfus.xebia_henri_potier.models.Book;
import cdreyfus.xebia_henri_potier.models.CommercialOffer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookInterface {

    @GET("books")
    Call<List<Book>> getBooks();

    @GET("books/{list_books}/commercialOffer")
    Call<CommercialOffer> getCommercialOffer(@Path("list_books") String list_books);

}
