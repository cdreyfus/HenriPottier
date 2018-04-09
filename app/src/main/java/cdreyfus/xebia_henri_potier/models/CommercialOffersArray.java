package cdreyfus.xebia_henri_potier.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommercialOffersArray {

    @SerializedName("offers")
    private
    List<CommercialOffer> commercialOffers;

    public  List<CommercialOffer>  getCommercialOffers() {
        return commercialOffers;
    }

    public void addOffer(CommercialOffer commercialOffer){
        commercialOffers.add(commercialOffer);
    }
}
