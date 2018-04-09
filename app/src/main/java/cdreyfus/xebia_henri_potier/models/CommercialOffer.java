package cdreyfus.xebia_henri_potier.models;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public abstract class CommercialOffer{

    public String type;
    public abstract float applyOffer(float basketOffer);



}