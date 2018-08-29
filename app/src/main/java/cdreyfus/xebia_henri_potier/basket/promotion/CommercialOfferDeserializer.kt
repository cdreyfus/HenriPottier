package cdreyfus.xebia_henri_potier.basket.promotion

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class CommercialOfferDeserializer : JsonDeserializer<CommercialOffer> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): CommercialOffer? {
        val jsonObject = json.asJsonObject
        val type = jsonObject.get("type")
        if (type != null) {
            when (type.asString) {
                "slice" -> return deserializeSlice(jsonObject)
                "percentage" -> return deserializePercentage(jsonObject)
                "minus" -> return deserializeMinus(jsonObject)
            }
        }
        return null
    }

    fun deserializePercentage(json: JsonElement): Percentage {
        val jsonObject = json.asJsonObject
        return Percentage(jsonObject.get("value").asInt)
    }

    fun deserializeMinus(json: JsonElement): Minus {
        val jsonObject = json.asJsonObject
        return Minus(jsonObject.get("value").asInt)
    }

    fun deserializeSlice(json: JsonElement): Slice {
        val jsonObject = json.asJsonObject
        return Slice(jsonObject.get("value").asInt, jsonObject.get("sliceValue").asInt)
    }
}