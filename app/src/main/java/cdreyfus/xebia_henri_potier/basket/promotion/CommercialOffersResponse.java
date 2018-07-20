package cdreyfus.xebia_henri_potier.basket.promotion;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CommercialOffersResponse {

    @SerializedName("offers")
    private
    List<CommercialOffer> commercialOffers;

    public CommercialOffersResponse(){
        commercialOffers = new ArrayList<>();
    }

    public  List<CommercialOffer>  getCommercialOffers() {
        return commercialOffers;
    }

    public void addOffer(CommercialOffer commercialOffer){
        commercialOffers.add(commercialOffer);
    }
}
