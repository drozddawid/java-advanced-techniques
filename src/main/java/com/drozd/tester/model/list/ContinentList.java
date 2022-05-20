package com.drozd.tester.model.list;

import com.drozd.tester.service.deserializer.ContinentListDeserializer;
import com.drozd.tester.model.entity.Link;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = ContinentListDeserializer.class)
public class ContinentList {
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("continents:items")
    private Link[] continentLinks;
    @JsonProperty("curies")
    private Link[] curies;
    @JsonProperty("self")
    private Link self;

    public ContinentList() {
    }

    public ContinentList(Integer count, Link[] continentLinks, Link[] curies, Link self) {
        this.count = count;
        this.continentLinks = continentLinks;
        this.curies = curies;
        this.self = self;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Link[] getContinentLinks() {
        return continentLinks;
    }

    public void setContinentLinks(Link[] continentLinks) {
        this.continentLinks = continentLinks;
    }

    public Link[] getCuries() {
        return curies;
    }

    public void setCuries(Link[] curies) {
        this.curies = curies;
    }

    public Link getSelf() {
        return self;
    }

    public void setSelf(Link self) {
        this.self = self;
    }
}
