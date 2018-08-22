package cdreyfus.xebia_henri_potier.book

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException

import java.lang.reflect.Type

import cdreyfus.xebia_henri_potier.book.Book

class BookDeserializer : JsonDeserializer<Book> {


    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Book {
        val jsonObject = json.asJsonObject

//        book.isbn = jsonObject.get("isbn").asString
//        book.title = jsonObject.get("title").asString
//        book.price = jsonObject.get("price").asInt
//        book.cover = jsonObject.get("cover").asString

        val synopsisJsonArray = jsonObject.getAsJsonArray("synopsis")
        val synopsis = StringBuilder()
        for (jsonElement in synopsisJsonArray) {
            synopsis.append(jsonElement.asString).append("\n")
        }

//        book.synopsis = synopsis.substring(0, synopsis.lastIndexOf("\n"))

        val book = Book(jsonObject.get("isbn").asString,
                jsonObject.get("title").asString,
                jsonObject.get("price").asInt,
                synopsis.substring(0, synopsis.lastIndexOf("\n")),
                jsonObject.get("cover").asString)

        return book
    }
}
