package com.drozd.tester.service.deserializer;

import com.drozd.tester.model.entity.Country;
import com.drozd.tester.model.entity.Link;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class CountryDeserializer extends JsonDeserializer<Country> {
    @Override
    public Country deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper om = new ObjectMapper();
        JsonNode root = om.readTree(jsonParser);
        Country country = new Country();
        country.setPopulation(root.get("population").intValue());
        country.setName(root.get("name").textValue());
        country.setIsoAlpha3(root.get("iso_alpha3").textValue());
        country.setIsoAlpha2(root.get("iso_alpha2").textValue());
        country.setGeonameId(root.get("geoname_id").intValue());
        country.setCurrencyCode(root.get("currency_code").textValue());
        root = root.get("_links");
        country.setSelf(om.readValue(root.get("self").traverse(), Link.class));
        country.setCuries(om.readValue(root.get("curies").traverse(), Link[].class));
        country.setSalaries(om.readValue(root.get("country:salaries").traverse(), Link.class));
        country.setContinentLink(om.readValue(root.get("country:continent").traverse(), Link.class));
        country.setAdmin1DivisionsListLink(om.readValue(root.get("country:admin1_divisions").traverse(), Link.class));

        return country;
    }
}
