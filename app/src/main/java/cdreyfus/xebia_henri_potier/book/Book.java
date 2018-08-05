package cdreyfus.xebia_henri_potier.book;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "books")
public class Book implements Parcelable{

//    private final static long serialVersionUID = 536871008;

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

    protected Book(Parcel in) {
        isbn = in.readString();
        title = in.readString();
        price = in.readInt();
        synopsis = in.readString();
        cover = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

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

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(isbn);
        dest.writeString(title);
        dest.writeInt(price);
        dest.writeString(synopsis);
        dest.writeString(cover);

    }
}
