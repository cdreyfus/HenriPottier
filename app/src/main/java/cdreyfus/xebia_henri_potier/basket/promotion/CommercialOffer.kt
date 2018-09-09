package cdreyfus.xebia_henri_potier.basket.promotion

sealed class CommercialOffer(var type:String) {

    abstract fun applyOffer(basketOffer: Float): Float
}

data class Minus(val value: Int) : CommercialOffer("minus") {

    override fun applyOffer(basketOffer: Float): Float {
        return basketOffer - value
    }
}

data class Percentage(var value: Int) : CommercialOffer("percentage") {

    override fun applyOffer(basketOffer: Float): Float {
        return basketOffer * (1 - value.toFloat() / 100)
    }
}

class Slice(var value: Int, var sliceValue: Int) : CommercialOffer("slice") {

    override fun applyOffer(basketOffer: Float): Float {
        return  basketOffer - basketOffer.toInt() / sliceValue * value
    }
}
