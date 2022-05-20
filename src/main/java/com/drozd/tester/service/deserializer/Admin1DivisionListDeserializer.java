package com.drozd.tester.service.deserializer;

import com.drozd.tester.model.entity.Link;
import com.drozd.tester.model.list.Admin1DivisionList;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Admin1DivisionListDeserializer extends JsonDeserializer<Admin1DivisionList> {
    @Override
    public Admin1DivisionList deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectMapper om = new ObjectMapper();

        JsonNode root = om.readTree(jsonParser);
        Admin1DivisionList admin1DivisionList = new Admin1DivisionList();
        admin1DivisionList.setCount(root.get("count").intValue());
        root = root.get("_links");
        admin1DivisionList.setSelf(om.readValue(root.get("self").traverse(), Link.class));
        admin1DivisionList.setCountry(om.readValue(root.get("a1:country").traverse(), Link.class));
        admin1DivisionList.setAdminDivisions(om.readValue(root.get("a1:items").traverse(), Link[].class));

        return admin1DivisionList;
    }
}
