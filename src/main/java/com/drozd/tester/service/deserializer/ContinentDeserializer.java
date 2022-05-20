package com.drozd.tester.service.deserializer;

import com.drozd.tester.model.entity.Continent;
import com.drozd.tester.model.entity.Link;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ContinentDeserializer extends JsonDeserializer<Continent> {
    @Override
    public Continent deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper om = new ObjectMapper();
        JsonNode root = om.readTree(jsonParser);
        Continent continent = new Continent();
        continent.setName(root.get("name").textValue());
        continent.setGeoCode(root.get("geonames_code").textValue());
        continent.setCountriesListLink(om.readValue(root.get("_links").get("continent:countries").traverse(), Link.class));
        continent.setUrbanAreasListLink(om.readValue(root.get("_links").get("continent:urban_areas").traverse(), Link.class));
        continent.setCuries(om.readValue(root.get("_links").get("curies").traverse(), Link[].class));
        continent.setSelf(om.readValue(root.get("_links").get("self").traverse(), Link.class));
        return continent;
    }
}
