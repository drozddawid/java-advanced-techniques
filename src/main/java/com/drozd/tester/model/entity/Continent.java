package com.drozd.tester.model.entity;

import com.drozd.tester.service.deserializer.ContinentDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javafx.beans.Observable;

import java.util.Arrays;

@JsonDeserialize(using = ContinentDeserializer.class)
public class Continent{
    @JsonProperty("geonames_code")
    private String geoCode;
    @JsonProperty("name")
    private String name;
    @JsonProperty("continent:countries")
    private Link countriesListLink;
    @JsonProperty("continent:urban_areas")
    private Link urbanAreasListLink;
    @JsonProperty("curies")
    private Link[] curies;
    @JsonProperty("self")
    private Link self;

    public Continent() {
    }

    public Continent(String geoCode, String name, Link countriesListLink, Link urbanAreasListLink, Link[] curies, Link self) {
        this.geoCode = geoCode;
        this.name = name;
        this.countriesListLink = countriesListLink;
        this.urbanAreasListLink = urbanAreasListLink;
        this.curies = curies;
        this.self = self;
    }

    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Link getCountriesListLink() {
        return countriesListLink;
    }

    public void setCountriesListLink(Link countriesListLink) {
        this.countriesListLink = countriesListLink;
    }

    public Link getUrbanAreasListLink() {
        return urbanAreasListLink;
    }

    public void setUrbanAreasListLink(Link urbanAreasListLink) {
        this.urbanAreasListLink = urbanAreasListLink;
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

    @Override
    public String toString() {
        return "Continent{" +
                "geoCode='" + geoCode + '\'' +
                ", name='" + name + '\'' +
                ", countriesListLink=" + countriesListLink +
                ", urbanAreasListLink=" + urbanAreasListLink +
                ", curies=" + Arrays.toString(curies) +
                ", self=" + self +
                '}';
    }
}
