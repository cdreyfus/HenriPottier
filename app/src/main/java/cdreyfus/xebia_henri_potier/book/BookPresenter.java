package cdreyfus.xebia_henri_potier.book;

import android.content.Context;

import cdreyfus.xebia_henri_potier.HenriPotierApplication;

public class BookPresenter {

    private View view;
    private Context context;

    BookPresenter(View view, Context context){
        this.view = view;
        this.context = context;
    }

    private void setTitre(String titre){
        view.setTitre(titre);
    }

    private void setCover(String cover){
        view.setCover(cover);
    }

    private void setSynopsis(String resume){
        view.setResume(resume);
    }

    private void setPrice(float price){
        view.setPrice(price);
    }

    public void getBook(String isbn){
        BookDao bookDao = ((HenriPotierApplication) context).getDaoSession().getBookDao();
        Book book =  bookDao.queryBuilder().where(BookDao.Properties.Isbn.eq(isbn)).build().unique();
        updateBook(book);
    }

    private void updateBook(Book book){
        setTitre(book.getTitle());
        setCover(book.getCover());
        setSynopsis(book.getSynopsis());
        setPrice(book.getPrice());
    }

    public interface View {

        void setTitre(String titre);

        void setCover(String cover);

        void setResume(String resume);

        void setPrice(float price);
    }

}


