package cdreyfus.xebia_henri_potier.models;

import org.junit.Assert;
import org.junit.Test;

import cdreyfus.xebia_henri_potier.basket.promotion.Minus;

public class MinusTest {

    @Test
    public void applyMinus(){
        Minus minus = new Minus(10);
        Assert.assertEquals(90.0, minus.applyMinus(100), 0);
    }
}