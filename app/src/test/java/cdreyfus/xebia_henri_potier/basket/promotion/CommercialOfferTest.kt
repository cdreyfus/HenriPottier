package cdreyfus.xebia_henri_potier.basket.promotion

import com.google.gson.GsonBuilder
import org.junit.Assert
import org.junit.Test


class CommercialOfferTest {

    @Test
    fun test_apply_percentage() {
        val percentage = Percentage(5)
        Assert.assertEquals(61.75, percentage.applyOffer(65f).toDouble(), 0.0)
    }

    @Test
    fun test_apply_minus() {
        val minus = Minus(5)
        Assert.assertEquals(60.0f, minus.applyOffer(65f), 0.0f)
    }

    @Test
    fun apply_slice_1() {
        val slice = Slice(12, 100)

        Assert.assertEquals(80f, slice.applyOffer(80f), 0f)
    }

    @Test
    fun apply_slice_2() {
        val slice = Slice(12, 100)

        Assert.assertEquals(226f, slice.applyOffer(250f), 0f)
    }

    @Test
    fun apply_slice_3() {
        val slice = Slice(12, 100)

        Assert.assertEquals(108f, slice.applyOffer(120f), 0f)
    }

    @Test
    fun test_deserialization() {
        val data: String = "{\n" +
                "      \"offers\": [\n" +
                "        {\n" +
                "          \"type\": \"percentage\",\n" +
                "          \"value\": 4\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"minus\",\n" +
                "          \"value\": 15\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"slice\",\n" +
                "          \"sliceValue\": 100,\n" +
                "          \"value\": 12\n" +
                "        }\n" +
                "      ]\n" +
                "    }"

        val gson = GsonBuilder()
                .registerTypeAdapter(CommercialOffer::class.java, CommercialOfferDeserializer())
                .create()

        val commercialOffersList = gson.fromJson(data, CommercialOffersList::class.java)

        for(offer in commercialOffersList.offers){
            when (offer.type) {
                "slice" -> {
                    assert((offer as Slice).value== 12)
                    assert(offer.sliceValue ==100)
                }
                "percentage" -> assert((offer as Percentage).value== 4)
                "minus" ->  assert((offer as Minus).value== 15)
            }
        }
    }
}