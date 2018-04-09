package cdreyfus.xebia_henri_potier.models.adapters;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import cdreyfus.xebia_henri_potier.models.CommercialOffer;

public class CommercialOfferDeserializer implements JsonDeserializer<CommercialOffer> {

    @Override
    public CommercialOffer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Get the "content" element from the parsed JSON
        JsonElement content = json.getAsJsonObject().get("offers");

        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
        return new Gson().fromJson(content, CommercialOffer.class);
    }
}