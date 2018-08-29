package cdreyfus.xebia_henri_potier.basket.promotion

abstract class CommercialOffer {

    var type: String? = null
    abstract fun applyOffer(basketOffer: Float): Float

}