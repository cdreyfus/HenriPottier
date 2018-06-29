package cdreyfus.xebia_henri_potier.basket.promotion;

public abstract class CommercialOffer{

    public String type;
    public abstract float applyOffer(float basketOffer);

    public CommercialOffer(){}

}