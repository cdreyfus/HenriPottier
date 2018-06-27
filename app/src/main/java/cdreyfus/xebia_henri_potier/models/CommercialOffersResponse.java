package cdreyfus.xebia_henri_potier.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CommercialOffersResponse {

    @SerializedName("offers")
    private
    List<CommercialOffer> commercialOffers;

    CommercialOffersResponse(){
        commercialOffers = new ArrayList<>();
    }

    public  List<CommercialOffer>  getCommercialOffers() {
        return commercialOffers;
    }

    public void addOffer(CommercialOffer commercialOffer){
        commercialOffers.add(commercialOffer);
    }
}
