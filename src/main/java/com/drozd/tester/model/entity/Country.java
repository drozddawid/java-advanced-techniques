
package com.drozd.tester.model.entity;

import com.drozd.tester.service.deserializer.CountryDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Arrays;

@JsonDeserialize(using = CountryDeserializer.class)
public class Country {

    @JsonProperty("country:admin1_divisions")
    private Link admin1DivisionsListLink;
    @JsonProperty("country:continent")
    private Link continentLink;
    @JsonProperty("country:salaries")
    private Link salaries;
    @JsonProperty("curies")
    private Link[] curies;
    @JsonProperty("self")
    private Link self;
    @JsonProperty("currency_code")
    private String currencyCode;
    @JsonProperty("geoname_id")
    private Integer geonameId;
    @JsonProperty("iso_alpha2")
    private String isoAlpha2;
    @JsonProperty("iso_alpha3")
    private String isoAlpha3;
    @JsonProperty("name")
    private String name;
    @JsonProperty("population")
    private Integer population;

    public Country() {
    }

    public Country(Link admin1DivisionsListLink, Link continentLink, Link salaries, Link[] curies, Link self, String currencyCode, Integer geonameId, String isoAlpha2, String isoAlpha3, String name, Integer population) {
        this.admin1DivisionsListLink = admin1DivisionsListLink;
        this.continentLink = continentLink;
        this.salaries = salaries;
        this.curies = curies;
        this.self = self;
        this.currencyCode = currencyCode;
        this.geonameId = geonameId;
        this.isoAlpha2 = isoAlpha2;
        this.isoAlpha3 = isoAlpha3;
        this.name = name;
        this.population = population;
    }

    @Override
    public String toString() {
        return "Country{" +
                "admin1DivisionsListLink=" + admin1DivisionsListLink +
                ", continentLink=" + continentLink +
                ", salaries=" + salaries +
                ", curies=" + Arrays.toString(curies) +
                ", self=" + self +
                ", currencyCode='" + currencyCode + '\'' +
                ", geonameId=" + geonameId +
                ", isoAlpha2='" + isoAlpha2 + '\'' +
                ", isoAlpha3='" + isoAlpha3 + '\'' +
                ", name='" + name + '\'' +
                ", population=" + population +
                '}';
    }

    public Link getAdmin1DivisionsListLink() {
        return admin1DivisionsListLink;
    }

    public void setAdmin1DivisionsListLink(Link admin1DivisionsListLink) {
        this.admin1DivisionsListLink = admin1DivisionsListLink;
    }

    public Link getContinentLink() {
        return continentLink;
    }

    public void setContinentLink(Link continentLink) {
        this.continentLink = continentLink;
    }

    public Link getSalaries() {
        return salaries;
    }

    public void setSalaries(Link salaries) {
        this.salaries = salaries;
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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(Integer geonameId) {
        this.geonameId = geonameId;
    }

    public String getIsoAlpha2() {
        return isoAlpha2;
    }

    public void setIsoAlpha2(String isoAlpha2) {
        this.isoAlpha2 = isoAlpha2;
    }

    public String getIsoAlpha3() {
        return isoAlpha3;
    }

    public void setIsoAlpha3(String isoAlpha3) {
        this.isoAlpha3 = isoAlpha3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }
}
