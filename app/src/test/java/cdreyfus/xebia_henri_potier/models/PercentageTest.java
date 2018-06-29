package cdreyfus.xebia_henri_potier.models;

import org.junit.Assert;
import org.junit.Test;

import cdreyfus.xebia_henri_potier.basket.Percentage;

public class PercentageTest {

    @Test
    public void testApplyPercentage(){
        Percentage percentage = new Percentage(5);
        Assert.assertEquals(61.75, percentage.applyPercentage(65), 0.0);
    }
}