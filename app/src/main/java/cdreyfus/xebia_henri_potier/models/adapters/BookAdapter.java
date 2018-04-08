package cdreyfus.xebia_henri_potier.models.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.json.JSONArray;

import java.io.IOException;

import cdreyfus.xebia_henri_potier.models.Book;

public class BookAdapter extends TypeAdapter<Book> {
    @Override
    public void write(JsonWriter writer, Book book) throws IOException {
        writer.beginObject();
        writer.name("isbn");
        writer.value(book.getIsbn());
        writer.name("title");
        writer.value(book.getTitle());
        writer.name("price");
        writer.value(book.getPrice());
        writer.name("cover");
        writer.value(book.getCover());
        writer.name("synopsis");

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(book.getSynopsis().split("\n"));
        writer.value(String.valueOf(jsonArray));

        writer.endObject();
    }

    @Override
    public Book read(JsonReader reader) throws IOException {
        Book book = new Book();
        reader.beginObject();
        String fieldname = null;

        while (reader.hasNext()){
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.NAME)) {
                //get the current token
                fieldname = reader.nextName();
            }

            assert fieldname != null;
            switch (fieldname){
                case "isbn":
                    book.setIsbn(reader.nextString());
                    break;

                case "title":
                    book.setTitle(reader.nextString());
                    break;

                case "price":
                    book.setPrice(reader.nextInt());
                    break;

                case "synopsis":
                    reader.beginArray();
                    StringBuilder synopsis = new StringBuilder();
                    while (reader.hasNext()) {
                        String paragraphe = reader.nextString();
                        synopsis.append(paragraphe).append("\n");
                    }
                    reader.endArray();
                    book.setSynopsis(synopsis.toString());
                    break;

                case "cover":
                    book.setCover(reader.nextString());
                    break;
            }
        }
        reader.endObject();
        return book;
    }
}
