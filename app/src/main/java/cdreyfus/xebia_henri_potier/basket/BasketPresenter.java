package cdreyfus.xebia_henri_potier.basket;

import android.annotation.SuppressLint;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import cdreyfus.xebia_henri_potier.HenriPotierApplication;
import cdreyfus.xebia_henri_potier.basket.promotion.CommercialOffer;
import cdreyfus.xebia_henri_potier.basket.promotion.CommercialOffersResponse;
import cdreyfus.xebia_henri_potier.basket.promotion.CommercialOfferApi;
import cdreyfus.xebia_henri_potier.book.Book;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class BasketPresenter {

    private Basket basket;
    private Book book;
    private View view;

    private Context context;

    public BasketPresenter(View view, Context context) {
        this.view = view;
        this.context = context;
        this.basket = new Basket(new HashMap<>());
    }

    public void setBasket(Basket basket){
        this.basket = basket;
    }

    private String generatePromoCode(Map<Book, Integer> map){
        StringBuilder promotionCode = new StringBuilder();
        for (Map.Entry<Book, Integer> entry : map.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                promotionCode.append(String.format("%s,", entry.getKey().getIsbn()));
            }
        }
        promotionCode = promotionCode.deleteCharAt(promotionCode.length() - 1);
        return promotionCode.toString();
    }

    private float calculateRegularPrice(Map<Book, Integer> map) {
        float regularPrice = 0;
        for (Map.Entry<Book, Integer> entry : map.entrySet()) {
            regularPrice += entry.getKey().getPrice() * entry.getValue();
        }
        return regularPrice;
    }

    @SuppressLint("CheckResult")
    public void setPrices() {

        float regularPrice = calculateRegularPrice(basket.getBooksQuantitiesMap());
        String promoCode = generatePromoCode(basket.getBooksQuantitiesMap());


        view.setRegularPrice(regularPrice);

        if (!basket.getBooksQuantitiesMap().isEmpty()) {
            CommercialOfferApi commercialOfferInterface = HenriPotierApplication.Companion.createCommercialOffferApi();

            Single<CommercialOffersResponse> singleCommercialOffer = commercialOfferInterface.getCommercialOffer(promoCode);

            singleCommercialOffer.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(commercialOffersResponse -> {
                        float finalPrice = applyBestCommercialOffer(commercialOffersResponse, regularPrice);
                        view.setFinalPrice(finalPrice);
                        view.setPromoValue(finalPrice - regularPrice);

                    }, Timber::d);
        } else {
            view.setFinalPrice(regularPrice);
            view.setPromoValue(0);
        }
    }

    public void updateBasketContent() {
        if (basket.getBooksQuantitiesMap().isEmpty()) {
            view.showEmpty();
        } else {
            view.showBooks(basket.getBooksQuantitiesMap());
        }
    }

    public void initBook(Book book){
        this.book = book;
    }

    public void setNumberPicker(){
        view.showNumberPicker(book.getTitle(), basket.getBooksQuantitiesMap().get(book));
    }


    public void editQuantityBook(int quantity) {
        for (Map.Entry<Book, Integer> entry : basket.getBooksQuantitiesMap().entrySet()) {
            if (entry.getKey().equals(book)) {
                entry.setValue(quantity);
            }
        }
    }

    public float applyBestCommercialOffer(CommercialOffersResponse commercialOffersResponse, float regularPrice) {
        float minimumValue = regularPrice;
        for (CommercialOffer commercialOffer : commercialOffersResponse.getCommercialOffers()) {
            minimumValue = Math.min(minimumValue, commercialOffer.applyOffer(regularPrice));
        }
        return minimumValue;
    }

    public interface View {

        void setRegularPrice(float regularPrice);

        void setFinalPrice(float finalPrice);

        void setPromoValue(float promoValue);

        void showBooks(HashMap<Book, Integer> listBooks);

        void showEmpty();

        void showNumberPicker(String title, int quantity);

        void hideNumberPicker();

    }

}
