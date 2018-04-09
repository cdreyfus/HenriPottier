package cdreyfus.xebia_henri_potier.models;

public class Minus extends CommercialOffer{
    private int value;

    Minus(int value) {
        this.value = value;
    }

    public float applyMinus(float basketValue){
        return basketValue - value;
    }

    @Override
    public float applyOffer(float basketValue) {
        return applyMinus(basketValue);
    }
}
