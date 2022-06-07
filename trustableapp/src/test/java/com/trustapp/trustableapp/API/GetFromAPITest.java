package com.trustapp.trustableapp.API;

import com.trustapp.trustableapp.DataClass.Nation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetFromAPITest {
    /**
     * this method tests whether the program can connect to the API
     * (might fail if countries provided by API change)
     */
    @Test
    void getData() {
        String expected = "[{\"countryCode\":\"AT\",\"countryName\":\"Austria\"},{\"countryCode\":\"BE\",\"countryName\"" +
                ":\"Belgium\"},{\"countryCode\":\"BG\",\"countryName\":\"Bulgaria\"},{\"countryCode\":\"CY\",\"countryName\":" +
                "\"Cyprus\"},{\"countryCode\":\"CZ\",\"countryName\":\"Czech Republic\"},{\"countryCode\":\"DE\"," +
                "\"countryName\":\"Germany\"},{\"countryCode\":\"DK\",\"countryName\":\"Denmark\"},{\"countryCode\":\"EE\"" +
                ",\"countryName\":\"Estonia\"},{\"countryCode\":\"EL\",\"countryName\":\"Greece\"},{\"countryCode\":\"ES\"" +
                ",\"countryName\":\"Spain\"},{\"countryCode\":\"EU\",\"countryName\":\"European Union\"},{\"countryCode\":\"FI\"," +
                "\"countryName\":\"Finland\"},{\"countryCode\":\"FR\",\"countryName\":\"France\"},{\"countryCode\":\"HR\"" +
                ",\"countryName\":\"Croatia\"},{\"countryCode\":\"HU\",\"countryName\":\"Hungary\"},{\"countryCode\"" +
                ":\"IE\",\"countryName\":\"Ireland\"},{\"countryCode\":\"IS\",\"countryName\":\"Iceland\"},{\"countryCode\"" +
                ":\"IT\",\"countryName\":\"Italy\"},{\"countryCode\":\"LI\",\"countryName\":\"Liechtenstein\"},{\"countryCode\"" +
                ":\"LT\",\"countryName\":\"Lithuania\"},{\"countryCode\":\"LU\",\"countryName\":\"Luxembourg\"}," +
                "{\"countryCode\":\"LV\",\"countryName\":\"Latvia\"},{\"countryCode\":\"MT\",\"countryName\":\"Malta\"}" +
                ",{\"countryCode\":\"NL\",\"countryName\":\"Netherlands\"},{\"countryCode\":\"NO\",\"countryName\":\"Norway\"}" +
                ",{\"countryCode\":\"PL\",\"countryName\":\"Poland\"},{\"countryCode\":\"PT\",\"countryName\":\"Portugal\"}" +
                ",{\"countryCode\":\"RO\",\"countryName\":\"Romania\"},{\"countryCode\":\"SE\",\"countryName\":\"Sweden\"}" +
                ",{\"countryCode\":\"SI\",\"countryName\":\"Slovenia\"},{\"countryCode\":\"SK\",\"countryName\":\"Slovakia\"}" +
                ",{\"countryCode\":\"UK\",\"countryName\":\"United Kingdom\"}]";

        GetFromAPI<Nation> getFromAPI = new GetFromAPI<>("https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list");
        assertEquals(expected, getFromAPI.getData());

    }
}