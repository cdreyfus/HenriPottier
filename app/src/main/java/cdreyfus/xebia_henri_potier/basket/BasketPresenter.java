package cdreyfus.xebia_henri_potier.basket;

import cdreyfus.xebia_henri_potier.models.Basket;
import cdreyfus.xebia_henri_potier.models.Book;

public class BasketPresenter {

    private Basket2 basket2;
    private View view;

    public BasketPresenter (View view){
        this.view = view;
        basket2 = Basket2.getInstance();
    }

    public void addBook(Book book){
        basket2.addBookToBasket(book);
        view.updateBasketContent();
    }

    public void removeBook(Book book){
        basket2.deleteBookFromBasket(book);
        view.updateBasketContent();
    }

    public void editQuantityBooks(Book book, int quantity){
        basket2.editQuantityBook(book, quantity);
        view.updateBasketContent();
    }

    public void applyPrice(){
        basket2.
    }


    public interface View {
        void updateBasketContent();
        void applyPrice();

    }
}
