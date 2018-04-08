package cdreyfus.xebia_henri_potier.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class Card {

    private static Card mInstance;
    private float totalPrice;
    private Map<String, Integer> booksQuantitiesMap;

    public static Card getInstance() {
        if (mInstance == null) {
            mInstance = new Card();
        }
        return mInstance;
    }

    private Card() {
        booksQuantitiesMap = new LinkedHashMap<>();
    }

    public void addBookToCart(String bookId){
        booksQuantitiesMap.put(bookId, 1);
    }

    public void deleteBookFromCart(String bookId){
        booksQuantitiesMap.remove(bookId);
    }

    public void changeQuantityBook(String bookId, int quantity){
        for(Map.Entry<String, Integer> entry: booksQuantitiesMap.entrySet()){
            if(entry.getKey().equals(bookId)){
                entry.setValue(quantity);
            }
        }
    }
}
