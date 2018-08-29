package cdreyfus.xebia_henri_potier.basket.promotion

class Slice(var value: Int, var sliceValue: Int) : CommercialOffer() {

    init {
        this.type = "slice"
    }

    fun applySlice(basketValue: Float): Float {
        return basketValue - basketValue.toInt() / sliceValue * value
    }

    override fun applyOffer(basketValue: Float): Float {
        return applySlice(basketValue)
    }
}
