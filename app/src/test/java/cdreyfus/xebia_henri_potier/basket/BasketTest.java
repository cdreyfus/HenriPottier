package cdreyfus.xebia_henri_potier.basket;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cdreyfus.xebia_henri_potier.basket.promotion.CommercialOffersResponse;
import cdreyfus.xebia_henri_potier.basket.promotion.Minus;
import cdreyfus.xebia_henri_potier.basket.promotion.Percentage;
import cdreyfus.xebia_henri_potier.basket.promotion.Slice;
import cdreyfus.xebia_henri_potier.book.Book;

public class BasketTest {

    private Basket basket;
    @Before
    public void setUp() {
        basket = new Basket(booksQuantitiesMap);
    }

    @Test
    public void getInstance(){
        Assert.assertEquals(0, basket.getBooksQuantitiesMap().size());
        Assert.assertEquals(0, basket.getRegularPrice(), 0.0);
    }

    @Test
    public void addBookToBasket() {
        Book book = new Book();
        book.setPrice(30);/home/cdreyfus/Projets/Xebia_Henri_Potier/GitProjects/android-using-kotlin/MyAddressBook-starter
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
    public void getRegularPrice(){
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
    public void applyBestCommercialOffer_1() {
        CommercialOffersResponse commercialOffersResponse = new CommercialOffersResponse();
        commercialOffersResponse.addOffer(new Slice(12, 100));
        commercialOffersResponse.addOffer(new Minus(15));
        commercialOffersResponse.addOffer(new Percentage(5));

        Book book1 = new Book();
        book1.setPrice(30);
        Book book2 = new Book();
        book2.setPrice(35);

        basket.addBookToBasket(book1);
        basket.addBookToBasket(book2);
        Assert.assertEquals(50, basket.applyBestCommercialOffer(commercialOffersResponse, basket.getRegularPrice()), 0);
    }

    @Test
    public void applyBestCommercialOffer_2() {
        CommercialOffersResponse commercialOffersResponse = new CommercialOffersResponse();
        commercialOffersResponse.addOffer(new Slice(12, 50));
        commercialOffersResponse.addOffer(new Minus(10));
        commercialOffersResponse.addOffer(new Percentage(5));

        Book book1 = new Book();
        book1.setPrice(30);
        Book book2 = new Book();
        book2.setPrice(35);

        basket.addBookToBasket(book1);
        basket.addBookToBasket(book2);
        Assert.assertEquals(53, basket.applyBestCommercialOffer(commercialOffersResponse, basket.getRegularPrice()), 0);

    }

    @Test
    public void applyBestCommercialOffer_3() {
        CommercialOffersResponse commercialOffersResponse = new CommercialOffersResponse();
        commercialOffersResponse.addOffer(new Slice(12, 100));
        commercialOffersResponse.addOffer(new Minus(15));
        commercialOffersResponse.addOffer(new Percentage(50));

        Book book1 = new Book();
        book1.setPrice(30);
        Book book2 = new Book();
        book2.setPrice(35);

        basket.addBookToBasket(book1);
        basket.addBookToBasket(book2);
        Assert.assertEquals(32.5, basket.applyBestCommercialOffer(commercialOffersResponse, basket.getRegularPrice()), 0);
    }

    @Test
    public void getPromotionCode(){
        Book book1 = new Book();
        book1.setIsbn("book1");
        Book book2 = new Book();
        book2.setIsbn("book2");

        basket.addBookToBasket(book1);
        basket.addBookToBasket(book2);
        basket.editQuantityBook(book1, 2);

        Assert.assertEquals("book1,book1,book2", basket.getPromotionCode());
    }

    @Test
    public void isEmpty() {
    }

    @Test
    public void getBooksQuantitiesMap() {
    }
}