package cdreyfus.xebia_henri_potier.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Basket {

    private static Basket mInstance;
    private Map<Book, Integer> booksQuantitiesMap;
    private float totalPrice;

    public static Basket getInstance() {
        if (mInstance == null) {
            mInstance = new Basket();
        }
        return mInstance;
    }

    Basket() {
        totalPrice = 0;
        booksQuantitiesMap = new LinkedHashMap<>();
    }

    public float getRegularPrice() {
        float regularPrice = 0;
        for (Map.Entry<Book, Integer> entry : booksQuantitiesMap.entrySet()) {
            regularPrice += entry.getKey().getPrice() * entry.getValue();
        }
        return regularPrice;
    }

    public Map<Book, Integer> getBooksQuantitiesMap() {
        return booksQuantitiesMap;
    }

    public void addBookToBasket(Book book) {
        booksQuantitiesMap.put(book, 1);
    }

    public void deleteBookFromBasket(Book book) {
        booksQuantitiesMap.remove(book);
    }

    public void editQuantityBook(Book book, int quantity) {
        for (Map.Entry<Book, Integer> entry : booksQuantitiesMap.entrySet()) {
            if (entry.getKey().getIsbn().equals(book.getIsbn())) {
                entry.setValue(quantity);
            }
        }
    }

    public float applyBestCommercialOffer(ArrayList<CommercialOffer> commercialOfferArrayList, float regularPrice){
        float minimumValue = regularPrice;

        for(CommercialOffer commercialOffer : commercialOfferArrayList){
            float promo = commercialOffer.applyOffer(regularPrice);
            minimumValue = Math.min(minimumValue, promo);
        }
        return minimumValue;
    }

    public float calculateTotalPrice() {
        //TODO request commercial offers
        ArrayList<CommercialOffer> commercialOffers = new ArrayList<>();
        return applyBestCommercialOffer(commercialOffers, getRegularPrice());
    }
}
