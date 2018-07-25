package cdreyfus.xebia_henri_potier.basket.promotion;

import org.junit.Assert;
import org.junit.Test;

import cdreyfus.xebia_henri_potier.basket.promotion.Slice;

public class SliceTest {

    @Test
    public void applySlice1() {
        Slice slice = new Slice(12, 100);

        Assert.assertEquals(80, slice.applySlice(80), 0);
    }

    @Test
    public void applySlice2() {
        Slice slice = new Slice(12, 100);

        Assert.assertEquals(226, slice.applySlice(250), 0);
    }

    @Test
    public void applySlice3() {
        Slice slice = new Slice(12, 100);

        Assert.assertEquals(108, slice.applySlice(120), 0);
    }
}