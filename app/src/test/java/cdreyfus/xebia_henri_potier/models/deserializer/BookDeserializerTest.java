package cdreyfus.xebia_henri_potier.models.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Type;

import cdreyfus.xebia_henri_potier.models.Book;

public class BookDeserializerTest {

    @Mock
    private
    JsonDeserializationContext context;
    @Mock
    private
    Type typeOfT;

    @Test
    public void deserialize() {
        BookDeserializer bookDeserializer = new BookDeserializer();

        Book book1 = new Book();
        book1.setIsbn("a460afed-e5e7-4e39-a39d-c885c05db861");
        book1.setTitle("Henri Potier et la Chambre des secrets");
        book1.setPrice(30);
        book1.setCover("http://henri-potier.xebia.fr/hp1.jpg");
        book1.setSynopsis("Paragraphe1.\nParagraphe2");

        String json = " {" +
                "        \"isbn\": \"a460afed-e5e7-4e39-a39d-c885c05db861\",\n" +
                "        \"title\": \"Henri Potier et la Chambre des secrets\",\n" +
                "        \"price\": 30,\n" +
                "        \"cover\": \"http://henri-potier.xebia.fr/hp1.jpg\",\n" +
                "        \"synopsis\": [\n" +
                "            \"Paragraphe1.\",\n" +
                "            \"Paragraphe2\"\n" +
                "        ]\n" +
                "    }";
        Gson gson = new Gson();
        JsonElement element = gson.fromJson(json, JsonElement.class);

        Book book2 =  bookDeserializer.deserialize(element, typeOfT, context);

        Assert.assertEquals(book1.getIsbn(),book2.getIsbn());
        Assert.assertEquals(book1.getTitle(),book2.getTitle());
        Assert.assertEquals(book1.getPrice(),book2.getPrice());
        Assert.assertEquals(book1.getCover(),book2.getCover());
        Assert.assertEquals(book1.getSynopsis(),book2.getSynopsis());
    }
}