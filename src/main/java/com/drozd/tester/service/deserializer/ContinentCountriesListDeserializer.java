package com.drozd.tester.service.deserializer;

import com.drozd.tester.model.entity.Link;
import com.drozd.tester.model.list.ContinentCountriesList;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ContinentCountriesListDeserializer extends JsonDeserializer<ContinentCountriesList> {
    @Override
    public ContinentCountriesList deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper om = new ObjectMapper();
        JsonNode root = om.readTree(jsonParser);
        ContinentCountriesList continentCountriesList = new ContinentCountriesList();
        continentCountriesList.setCount(root.get("count").intValue());
        root = root.get("_links");
        continentCountriesList.setContinentCountriesList(om.readValue(root.get("country:items").traverse(), Link[].class));
        continentCountriesList.setCuries(om.readValue(root.get("curies").traverse(), Link[].class));
        continentCountriesList.setSelf(om.readValue(root.get("self").traverse(), Link.class));

        return continentCountriesList;
    }
}
