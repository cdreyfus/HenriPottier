package cdreyfus.xebia_henri_potier.basket.promotion

class Minus(val value: Int) : CommercialOffer() {

    init {
        this.type = "minus"
    }

    private fun applyMinus(basketValue: Float): Float {
        return basketValue - value
    }


    override fun applyOffer(basketOffer: Float): Float {
        return applyMinus(basketOffer)
    }
}
