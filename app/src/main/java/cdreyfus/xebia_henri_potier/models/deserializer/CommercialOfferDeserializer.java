package cdreyfus.xebia_henri_potier.models.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import cdreyfus.xebia_henri_potier.basket.promotion.CommercialOffer;
import cdreyfus.xebia_henri_potier.basket.promotion.Minus;
import cdreyfus.xebia_henri_potier.basket.promotion.Percentage;
import cdreyfus.xebia_henri_potier.basket.promotion.Slice;

public class CommercialOfferDeserializer implements JsonDeserializer<CommercialOffer> {

    @Override
    public CommercialOffer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement type = jsonObject.get("type");
        if (type != null) {
            switch (type.getAsString()) {
                case "slice":
                    return deserializeSlice(jsonObject);
                case "percentage":
                    return deserializePercentage(jsonObject);
                case "minus":
                    return deserializeMinus(jsonObject);
            }
        }
        return null;
    }

    public Percentage deserializePercentage(JsonElement json) {
        JsonObject jsonObject = json.getAsJsonObject();
        return new Percentage(jsonObject.get("value").getAsInt());
    }

    public Minus deserializeMinus(JsonElement json) {
        JsonObject jsonObject = json.getAsJsonObject();
        return new Minus(jsonObject.get("value").getAsInt());
    }

    public Slice deserializeSlice(JsonElement json) {
        JsonObject jsonObject = json.getAsJsonObject();
        return new Slice(jsonObject.get("value").getAsInt(), jsonObject.get("sliceValue").getAsInt());
    }
}