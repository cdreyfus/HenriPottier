package cdreyfus.xebia_henri_potier.basket.promotion

import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class CommercialOffersResponse {

    @SerializedName("offers")
    private val commercialOffers: MutableList<CommercialOffer>

    init {
        commercialOffers = ArrayList()
    }

    fun getCommercialOffers(): List<CommercialOffer> {
        return commercialOffers
    }

    fun addOffer(commercialOffer: CommercialOffer) {
        commercialOffers.add(commercialOffer)
    }
}
