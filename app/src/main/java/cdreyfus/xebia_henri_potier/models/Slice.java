package cdreyfus.xebia_henri_potier.models;

public class Slice extends CommercialOffer{
    private int value;
    private int sliceValue;

    public Slice(int value, int sliceValue) {
        this.type="slice";
        this.value = value;
        this.sliceValue = sliceValue;
    }

    public int getSliceValue() {
        return sliceValue;
    }

    public void setSliceValue(int sliceValue) {
        this.sliceValue = sliceValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public float applySlice(float basketValue){
        return basketValue - ((int) basketValue / sliceValue) * value;
    }

    @Override
    public float applyOffer(float basketValue) {
        return applySlice(basketValue);
    }
}
