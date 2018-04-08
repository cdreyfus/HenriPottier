package cdreyfus.xebia_henri_potier.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.lang.reflect.Type;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "books")
public class Book {

    @Id
    private String isbn;

    @Property(nameInDb = "title")
    private String title;

    @Property(nameInDb = "price")
    private int price;

    @Property(nameInDb = "synopsis")
    private String synopsis;

    @Property(nameInDb = "cover")
    private String cover;

    public Book() {

    }

    @Generated(hash = 763582126)
    public Book(String isbn, String title, int price, String synopsis,
            String cover) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
        this.synopsis = synopsis;
        this.cover = cover;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getImageUrl() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getCover() {
        return this.cover;
    }
}
