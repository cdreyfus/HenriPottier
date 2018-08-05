package cdreyfus.xebia_henri_potier.basket;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

import cdreyfus.xebia_henri_potier.book.Book;

public class Basket implements Parcelable {


    private HashMap<Book, Integer> booksQuantitiesMap;

    public Basket(HashMap<Book, Integer> booksQuantitiesMap) {
        this.booksQuantitiesMap = booksQuantitiesMap;
    }

    public HashMap<Book, Integer> getBooksQuantitiesMap() {
        return booksQuantitiesMap;
    }

    public void setBooksQuantitiesMap(HashMap<Book, Integer> booksQuantitiesMap) {
        this.booksQuantitiesMap = booksQuantitiesMap;
    }


    protected Basket(Parcel in) {
        booksQuantitiesMap = new HashMap<>();
        int size = in.readInt();
        for(int i = 0; i < size; i++){
            Book book = (Book) in.readValue(Book.class.getClassLoader());
            int value = in.readInt();
            booksQuantitiesMap.put(book,value);
        }
    }

    public static final Creator<Basket> CREATOR = new Creator<Basket>() {
        @Override
        public Basket createFromParcel(Parcel in) {
            return new Basket(in);
        }

        @Override
        public Basket[] newArray(int size) {
            return new Basket[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(booksQuantitiesMap.size());
        for(HashMap.Entry<Book,Integer> entry : booksQuantitiesMap.entrySet()){
            dest.writeValue(entry.getKey());
            dest.writeInt(entry.getValue());
        }
    }
}