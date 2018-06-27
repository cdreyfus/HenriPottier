package cdreyfus.xebia_henri_potier.models;

public abstract class CommercialOffer{

    public String type;
    public abstract float applyOffer(float basketOffer);

    public CommercialOffer(){}

}