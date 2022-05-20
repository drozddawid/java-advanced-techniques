package com.drozd.tester.service.deserializer;

import com.drozd.tester.model.list.ContinentList;
import com.drozd.tester.model.entity.Link;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ContinentListDeserializer extends JsonDeserializer<ContinentList> {
    @Override
    public ContinentList deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper om = new ObjectMapper();
        JsonNode root = om.readTree(jsonParser);
        ContinentList continentList = new ContinentList();
        continentList.setCount(root.get("count").intValue());
        continentList.setContinentLinks(om.readValue(root.get("_links").get("continent:items").traverse(), Link[].class));
        continentList.setCuries(om.readValue(root.get("_links").get("curies").traverse(), Link[].class));
        continentList.setSelf(om.readValue(root.get("_links").get("self").traverse(), Link.class));
        return continentList;
    }
}
