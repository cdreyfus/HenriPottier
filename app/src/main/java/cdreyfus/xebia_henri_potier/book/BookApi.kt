package cdreyfus.xebia_henri_potier.book

import retrofit2.Call
import retrofit2.http.GET

interface BookApi {

    @get:GET("books")
    val books: Call<ArrayList<Book>>

}
