package cdreyfus.xebia_henri_potier.book;

import android.os.Parcel;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BookTest {


    @Test
    public void test_book_is_parcelable() {
        Book book = new Book();
        book.setIsbn("c8fabf68-8374-48fe-a7ea-a00ccd07afff");
        book.setTitle("Henri Potier à l'école des sorciers");
        book.setPrice(35);

        Parcel parcel = Parcel.obtain();
        book.writeToParcel(parcel, book.describeContents());
        parcel.setDataPosition(0);

        Book createdFromParcel = Book.CREATOR.createFromParcel(parcel);
        assertThat(createdFromParcel.getIsbn(), is("c8fabf68-8374-48fe-a7ea-a00ccd07afff"));
        assertThat(createdFromParcel.getTitle(), is("Henri Potier à l'école des sorciers"));
        assertThat(createdFromParcel.getPrice(), is(35));
    }
}