package cdreyfus.xebia_henri_potier.models.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Type;

import cdreyfus.xebia_henri_potier.basket.promotion.CommercialOfferDeserializer;
import cdreyfus.xebia_henri_potier.basket.promotion.Minus;
import cdreyfus.xebia_henri_potier.basket.promotion.Percentage;
import cdreyfus.xebia_henri_potier.basket.promotion.Slice;

public class CommercialOfferDeserializerTest {

    @Mock
    private
    JsonDeserializationContext context;
    @Mock
    private
    Type typeOfT;

    @Test
    public void deserializePercentage() {

        CommercialOfferDeserializer commercialOfferDeserializer = new CommercialOfferDeserializer();

        String data = "{\n" +
                "\"type\": \"percentage\",\n" +
                "\"value\": 5\n" +
                "}";

        Gson gson = new Gson();
        JsonElement element = gson.fromJson(data, JsonElement.class);
        Assert.assertEquals(Percentage.class, commercialOfferDeserializer.deserializePercentage(element).getClass());
        Assert.assertEquals(5, commercialOfferDeserializer.deserializePercentage(element).getValue());
    }

    @Test
    public void deserializeMinus() {

        CommercialOfferDeserializer commercialOfferDeserializer = new CommercialOfferDeserializer();

        String data = "{\n" +
                "\"type\": \"minus\",\n" +
                "\"value\": 15\n" +
                "}";

        Gson gson = new Gson();
        JsonElement element = gson.fromJson(data, JsonElement.class);
        Assert.assertEquals(Minus.class, commercialOfferDeserializer.deserializeMinus(element).getClass());
        Assert.assertEquals(15, commercialOfferDeserializer.deserializePercentage(element).getValue());
    }

    @Test
    public void deserializeSlice() {

        CommercialOfferDeserializer commercialOfferDeserializer = new CommercialOfferDeserializer();

        String data = "              {\n" +
                "                \"type\": \"slice\",\n" +
                "                \"sliceValue\": 100,\n" +
                "                \"value\": 12\n" +
                "              }";

        Gson gson = new Gson();
        JsonElement element = gson.fromJson(data, JsonElement.class);
        Assert.assertEquals(Slice.class, commercialOfferDeserializer.deserializeSlice(element).getClass());
        Assert.assertEquals(12, commercialOfferDeserializer.deserializeSlice(element).getValue());
        Assert.assertEquals(100, commercialOfferDeserializer.deserializeSlice(element).getSliceValue());

    }

    @Test
    public void deserializeCommercialOfferPercentage() {

        CommercialOfferDeserializer commercialOfferDeserializer = new CommercialOfferDeserializer();
        Percentage percentage = new Percentage(5);

        String data = "{\n" +
                "\"type\": \"percentage\",\n" +
                "\"value\": 5\n" +
                "}";

        Gson gson = new Gson();
        JsonElement element = gson.fromJson(data, JsonElement.class);
        Assert.assertEquals(percentage.getClass(), commercialOfferDeserializer.deserialize(element, typeOfT, context).getClass());
        Assert.assertEquals(percentage.getValue(), ((Percentage) commercialOfferDeserializer.deserialize(element, typeOfT, context)).getValue());
    }

    @Test
    public void deserializeCommercialOfferSlice() {

        CommercialOfferDeserializer commercialOfferDeserializer = new CommercialOfferDeserializer();

        String data = "              {\n" +
                "                \"type\": \"slice\",\n" +
                "                \"sliceValue\": 100,\n" +
                "                \"value\": 12\n" +
                "              }";

        Gson gson = new Gson();
        JsonElement element = gson.fromJson(data, JsonElement.class);
        Assert.assertEquals(Slice.class, commercialOfferDeserializer.deserialize(element, typeOfT, context).getClass());
        Assert.assertEquals(12, ((Slice) commercialOfferDeserializer.deserialize(element, typeOfT, context)).getValue());
        Assert.assertEquals(100, ((Slice) commercialOfferDeserializer.deserialize(element, typeOfT, context)).getSliceValue());

    }

    @Test
    public void deserializeCommercialOfferMinus() {

        CommercialOfferDeserializer commercialOfferDeserializer = new CommercialOfferDeserializer();

        String data = "{\n" +
                "\"type\": \"minus\",\n" +
                "\"value\": 15\n" +
                "}";

        Gson gson = new Gson();
        JsonElement element = gson.fromJson(data, JsonElement.class);

        Assert.assertEquals(Minus.class, commercialOfferDeserializer.deserialize(element, typeOfT, context).getClass());
        Assert.assertEquals(15, ((Minus) commercialOfferDeserializer.deserialize(element, typeOfT, context)).getValue());
    }
}