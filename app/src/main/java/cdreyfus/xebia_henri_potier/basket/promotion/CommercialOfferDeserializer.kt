package cdreyfus.xebia_henri_potier.basket.promotion

import com.google.gson.*
import java.lang.reflect.Type

class CommercialOfferDeserializer : JsonDeserializer<CommercialOffer> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): CommercialOffer? {
        val jsonObject = json.asJsonObject
        val type = jsonObject.get("type")
        if (type != null) {
            when (type.asString) {
                "slice" -> return Gson().fromJson(jsonObject, Slice::class.java)
                "percentage" -> return Gson().fromJson(jsonObject, Percentage::class.java)
                "minus" -> return Gson().fromJson(jsonObject, Minus::class.java)
            }
        }
        return null
    }
}