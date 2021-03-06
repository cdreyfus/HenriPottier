package cdreyfus.xebia_henri_potier.basket.promotion;

public class Minus extends CommercialOffer {
    private int value;

    public Minus(int value) {
        this.type = "minus";
        this.value = value;
    }

    public float applyMinus(float basketValue){
        return basketValue - value;
    }

    public int getValue() {
        return value;
    }


    @Override
    public float applyOffer(float basketValue) {
        return applyMinus(basketValue);
    }
}
