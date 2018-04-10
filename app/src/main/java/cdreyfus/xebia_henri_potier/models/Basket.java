package cdreyfus.xebia_henri_potier.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class Basket {

    private static Basket mInstance;
    private LinkedHashMap<Book, Integer> booksQuantitiesMap;

    public static Basket getInstance() {
        if (mInstance == null) {
            mInstance = new Basket();
        }
        return mInstance;
    }

    Basket() {
        booksQuantitiesMap = new LinkedHashMap<>();
    }

    public float getRegularPrice() {
        float regularPrice = 0;
        for (Map.Entry<Book, Integer> entry : booksQuantitiesMap.entrySet()) {
            regularPrice += entry.getKey().getPrice() * entry.getValue();
        }
        return regularPrice;
    }

    public LinkedHashMap<Book, Integer> getBooksQuantitiesMap() {
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
            if (entry.getKey().equals(book)) {
                entry.setValue(quantity);
            }
        }
    }

    public float applyBestCommercialOffer(CommercialOffersResponse commercialOffersResponse, float regularPrice){
        float minimumValue = regularPrice;
        for (CommercialOffer commercialOffer: commercialOffersResponse.getCommercialOffers()){
            minimumValue = Math.min(minimumValue, commercialOffer.applyOffer(regularPrice));
        }
        return minimumValue;
    }

    public String getPromotionCode(){
        StringBuilder promotionCode = new StringBuilder();
        for (Map.Entry<Book, Integer> entry : booksQuantitiesMap.entrySet()){
            for(int i=0; i<entry.getValue(); i++){
                promotionCode.append(String.format("%s,", entry.getKey().getIsbn()));
            }
        }
        promotionCode = promotionCode.deleteCharAt(promotionCode.length()-1);

        return promotionCode.toString();
    }

    public float getPromotionValue(CommercialOffersResponse commercialOffersResponse) {
        float regularPrice = getRegularPrice();
        return regularPrice - applyBestCommercialOffer(commercialOffersResponse, regularPrice);
    }
}
