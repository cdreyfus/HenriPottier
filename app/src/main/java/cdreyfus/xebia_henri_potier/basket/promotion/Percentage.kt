package cdreyfus.xebia_henri_potier.basket.promotion

class Percentage(percentage: Int) : CommercialOffer() {
    var value: Int = 0
        private set

    init {
        this.type = "percentage"
        this.value = percentage
    }

    fun applyPercentage(basketValue: Float): Float {
        return basketValue * (1 - value.toFloat() / 100)

    }

    fun setPercentage(percentage: Int) {
        this.value = percentage
    }

    override fun applyOffer(basketValue: Float): Float {
        return applyPercentage(basketValue)
    }
}
