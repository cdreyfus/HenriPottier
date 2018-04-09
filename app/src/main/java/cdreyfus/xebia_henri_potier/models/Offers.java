package cdreyfus.xebia_henri_potier.models;

import java.util.ArrayList;
import java.util.List;

public class Offers {
    Slice slice;
    Percentage percentage;
    Minus minus;

    public List<CommercialOffer> getListCommercialOffers(){
        List<CommercialOffer> list = new ArrayList<>();
        list.add(slice);
        list.add(percentage);
        list.add(minus);
        return list;
    }
}
