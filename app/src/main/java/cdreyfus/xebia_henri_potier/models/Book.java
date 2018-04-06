package cdreyfus.xebia_henri_potier.models;

public class Book extends CommercialOffer{
    private String isbn;
    private String title;
    private int price;
    private String synopsis;


    public Book(String isbn, String title, int price, String synopsis) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
        this.synopsis = synopsis;
    }

    @Override
    public void applyCommercialOffer() {

    }
}
