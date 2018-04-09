package cdreyfus.xebia_henri_potier.models.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import cdreyfus.xebia_henri_potier.models.CommercialOffer;
import cdreyfus.xebia_henri_potier.models.Minus;
import cdreyfus.xebia_henri_potier.models.Percentage;
import cdreyfus.xebia_henri_potier.models.Slice;

public class CommercialOfferDeserializer implements JsonDeserializer<CommercialOffer> {

    @Override
    public CommercialOffer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement type = jsonObject.get("type");
        if (type != null) {
            switch (type.getAsString()) {
                case "slice":
                    return context.deserialize(jsonObject, Slice.class);
                case "percentage":
                    return context.deserialize(jsonObject, Percentage.class);
                case "minus":
                    return context.deserialize(jsonObject, Minus.class);
            }
        }
        return null;
    }
}