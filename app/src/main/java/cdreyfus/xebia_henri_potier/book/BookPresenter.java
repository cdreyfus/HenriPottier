package cdreyfus.xebia_henri_potier.book;

import android.content.Context;

import cdreyfus.xebia_henri_potier.HenriPotierApplication;
import cdreyfus.xebia_henri_potier.basket.Basket;

public class BookPresenter {

    private View view;
    private Context context;

    BookPresenter(View view, Context context) {
        this.view = view;
        this.context = context;
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

    public void getBook(String isbn) {
        BookDao bookDao = ((HenriPotierApplication) context).getDaoSession().getBookDao();
        Book book = bookDao.queryBuilder().where(BookDao.Properties.Isbn.eq(isbn)).build().unique();
        updateBook(book);
        updateBasketButtons(book);
    }

    private void updateBook(Book book) {
        setTitre(book.getTitle());
        setCover(book.getCover());
        setSynopsis(book.getSynopsis());
        setPrice(book.getPrice());
    }

    private void updateBasketButtons(Book book) {
        Basket basket = Basket.getInstance();
        if (basket.getBooksQuantitiesMap().containsKey(book)) {
            view.setBookInBasket(basket.getBooksQuantitiesMap().get(book));
        } else {
            view.setNotInBasket();
        }
    }

    public void addToBasket(String isbn) {
        Basket basket = Basket.getInstance();
        BookDao bookDao = ((HenriPotierApplication) context).getDaoSession().getBookDao();
        Book book = bookDao.queryBuilder().where(BookDao.Properties.Isbn.eq(isbn)).build().unique();
        basket.addBookToBasket(book);
        view.setBookInBasket(basket.getBooksQuantitiesMap().get(book));
    }

    public void removeFromBasket(String isbn) {
        Basket basket = Basket.getInstance();
        BookDao bookDao = ((HenriPotierApplication) context).getDaoSession().getBookDao();
        Book book = bookDao.queryBuilder().where(BookDao.Properties.Isbn.eq(isbn)).build().unique();
        basket.deleteBookFromBasket(book);
        view.setNotInBasket();
    }

    public void initNumberPicker(String isbn) {
        BookDao bookDao = ((HenriPotierApplication) context).getDaoSession().getBookDao();
        Book book = bookDao.queryBuilder().where(BookDao.Properties.Isbn.eq(isbn)).build().unique();
        view.showNumberPicker(book.getTitle(), Basket.getInstance().getBooksQuantitiesMap().get(book));

    }

    public void editQuantityinBasket(String isbn, int newVal) {
        BookDao bookDao = ((HenriPotierApplication) context).getDaoSession().getBookDao();
        Book book = bookDao.queryBuilder().where(BookDao.Properties.Isbn.eq(isbn)).build().unique();

        Basket basket = Basket.getInstance();
        basket.editQuantityBook(book, newVal);

        view.hideNumberPicker();
        view.setBookInBasket(newVal);
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


