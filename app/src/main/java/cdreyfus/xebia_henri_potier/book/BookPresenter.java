package cdreyfus.xebia_henri_potier.book;

import android.content.Context;

import java.util.Map;

import cdreyfus.xebia_henri_potier.HenriPotierApplication;
import cdreyfus.xebia_henri_potier.basket.Basket;

public class BookPresenter {

    private View view;
    private Context context;
    private Book book;
    private Basket basket;

    BookPresenter(View view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void initBook(String isbn) {
        BookDao bookDao = ((HenriPotierApplication) context).getDaoSession().getBookDao();
        this.book = bookDao.queryBuilder().where(BookDao.Properties.Isbn.eq(isbn)).build().unique();
    }

    public void initBasket(Basket mBasket) {
        this.basket = mBasket;
    }


    private void setTitre(String titre) {
        view.setTitre(titre);
    }

    private void setCover(String cover) {
        view.setCover(cover);
    }

    private void setSynopsis(String resume) {
        view.setResume(resume);
    }

    private void setPrice(float price) {
        view.setPrice(price);
    }

    public void getBook() {
        updateBook();
        updateBasketButtons();
    }

    private void updateBook() {
        setTitre(book.getTitle());
        setCover(book.getCover());
        setSynopsis(book.getSynopsis());
        setPrice(book.getPrice());
    }

    private void updateBasketButtons() {
        if (basket.getBooksQuantitiesMap().containsKey(book)) {
            view.setBookInBasket(basket.getBooksQuantitiesMap().get(book));
        } else {
            view.setNotInBasket();
        }
    }

    public void addToBasket() {
        basket.getBooksQuantitiesMap().put(book, 1);
        view.setBookInBasket(basket.getBooksQuantitiesMap().get(book));
    }

    public void removeFromBasket() {
        basket.getBooksQuantitiesMap().remove(book);
        view.setNotInBasket();
    }

    public void editQuantityinBasket(int newVal) {
        for (Map.Entry<Book, Integer> entry : basket.getBooksQuantitiesMap().entrySet()) {
            if (entry.getKey().equals(book)) {
                entry.setValue(newVal);
            }
        }
        view.setBookInBasket(newVal);
        view.hideNumberPicker();
    }

    public void selectQuantityInBasket(){
        view.showNumberPicker(book.getTitle(), basket.getBooksQuantitiesMap().get(book));
    }


    public interface View {

        void setTitre(String titre);

        void setCover(String cover);

        void setResume(String resume);

        void setPrice(float price);

        void setBookInBasket(int quantity);

        void setNotInBasket();

        void showNumberPicker(String title, int quantity);

        void hideNumberPicker();
    }

}


