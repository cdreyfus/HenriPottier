package cdreyfus.xebia_henri_potier.book;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import cdreyfus.xebia_henri_potier.book.Book;

public class BookDeserializer implements JsonDeserializer<Book> {
    @Override
    public Book deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Book book = new Book();
        book.setIsbn(jsonObject.get("isbn").getAsString());
        book.setTitle(jsonObject.get("title").getAsString());
        book.setPrice(jsonObject.get("price").getAsInt());
        book.setCover(jsonObject.get("cover").getAsString());

        JsonArray synopsisJsonArray = jsonObject.getAsJsonArray("synopsis");
        StringBuilder synopsis = new StringBuilder();
        for(JsonElement jsonElement : synopsisJsonArray){
            synopsis.append(jsonElement.getAsString()).append("\n");
        }

        book.setSynopsis(synopsis.substring(0, synopsis.lastIndexOf("\n")));

        return book;
    }
}
