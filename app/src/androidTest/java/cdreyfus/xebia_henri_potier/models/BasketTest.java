package cdreyfus.xebia_henri_potier.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class BasketTest {

    private Basket basket;
    @Before
    public void setUp() {
        basket = new Basket();
    }

    @Test
    public void testCreateBasket(){
        Assert.assertEquals(0, basket.getBooksQuantitiesMap().size());
        Assert.assertEquals(0, basket.getRegularPrice(), 0.0);
    }

    @Test
    public void addBookToBasket() {
        Book book = new Book();
        book.setPrice(30);
        basket.addBookToBasket(book);

        Assert.assertEquals(1, basket.getBooksQuantitiesMap().get(book).intValue());
    }

    @Test
    public void deleteBookFromBasket() {
        Book book = new Book();
        basket.addBookToBasket(book);

        Assert.assertTrue(basket.getBooksQuantitiesMap().containsKey(book));
        basket.deleteBookFromBasket(book);
        Assert.assertFalse(basket.getBooksQuantitiesMap().containsKey(book));
    }

    @Test
    public void editQuantityBook(){
        Book book = new Book();
        basket.addBookToBasket(book);
        basket.editQuantityBook(book, 4);

        Assert.assertEquals(4, basket.getBooksQuantitiesMap().get(book).intValue());
    }

    @Test
    public void testGetRegularPrice(){
        Book book1 = new Book();
        book1.setPrice(30);
        Book book2 = new Book();
        book2.setPrice(35);

        basket.addBookToBasket(book1);
        basket.addBookToBasket(book2);

        basket.editQuantityBook(book1, 3);
        Assert.assertEquals(125, basket.getRegularPrice(), 0.0f);
    }

    @Test
    public void applyBestCommercialOffer1() {
        ArrayList<CommercialOffer> commercialOffers = new ArrayList<>();
        commercialOffers.add(new Slice(12, 100));
        commercialOffers.add(new Minus(15));
        commercialOffers.add(new Percentage(5));

        Book book1 = new Book();
        book1.setPrice(30);
        Book book2 = new Book();

        book2.setPrice(35);

        basket.addBookToBasket(book1);
        basket.addBookToBasket(book2);
        Assert.assertEquals(50, basket.applyBestCommercialOffer(commercialOffers, basket.getRegularPrice()), 0);

    }

    @Test
    public void applyBestCommercialOffer2() {
        ArrayList<CommercialOffer> commercialOffers = new ArrayList<>();
        commercialOffers.add(new Slice(12, 50));
        commercialOffers.add(new Minus(10));
        commercialOffers.add(new Percentage(5));

        Book book1 = new Book();
        book1.setPrice(30);
        Book book2 = new Book();
        book2.setPrice(35);

        basket.addBookToBasket(book1);
        basket.addBookToBasket(book2);
        Assert.assertEquals(53, basket.applyBestCommercialOffer(commercialOffers, basket.getRegularPrice()), 0);

    }

    @Test
    public void applyBestCommercialOffer3() {
        ArrayList<CommercialOffer> commercialOffers = new ArrayList<>();
        commercialOffers.add(new Slice(12, 100));
        commercialOffers.add(new Minus(15));
        commercialOffers.add(new Percentage(50));

        Book book1 = new Book();
        book1.setPrice(30);
        Book book2 = new Book();
        book2.setPrice(35);

        basket.addBookToBasket(book1);
        basket.addBookToBasket(book2);
        Assert.assertEquals(32.5, basket.applyBestCommercialOffer(commercialOffers, basket.getRegularPrice()), 0);
    }

}