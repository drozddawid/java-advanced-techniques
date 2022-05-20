package com.drozd.tester.model.list;

import com.drozd.tester.model.entity.Link;
import com.drozd.tester.service.deserializer.ContinentCountriesListDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Arrays;
@JsonDeserialize(using = ContinentCountriesListDeserializer.class)
public class ContinentCountriesList {
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("country:items")
    private Link[] continentCountriesList;
    @JsonProperty("curies")
    private Link[] curies;
    @JsonProperty("self")
    private Link self;

    public ContinentCountriesList() {
    }

    public ContinentCountriesList(Integer count, Link[] continentCountriesList, Link[] curies, Link self) {
        this.count = count;
        this.continentCountriesList = continentCountriesList;
        this.curies = curies;
        this.self = self;
    }

    @Override
    public String toString() {
        return "ContinentCountriesList{" +
                "count=" + count +
                ", continentCountriesList=" + Arrays.toString(continentCountriesList) +
                ", curies=" + Arrays.toString(curies) +
                ", self=" + self +
                '}';
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Link[] getContinentCountriesList() {
        return continentCountriesList;
    }

    public void setContinentCountriesList(Link[] continentCountriesList) {
        this.continentCountriesList = continentCountriesList;
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
