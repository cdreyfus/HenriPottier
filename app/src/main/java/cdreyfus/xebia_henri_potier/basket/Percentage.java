package cdreyfus.xebia_henri_potier.basket;

import cdreyfus.xebia_henri_potier.basket.CommercialOffer;

public class Percentage extends CommercialOffer {
    private  int percentage;

    public Percentage(int percentage) {
        this.type = "percentage";
        this.percentage = percentage;
    }

    public float applyPercentage(float basketValue){
        return basketValue * (1 - (float) percentage/100);

    }

    public int getValue() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public float applyOffer(float basketValue) {
        return applyPercentage(basketValue);
    }
}
