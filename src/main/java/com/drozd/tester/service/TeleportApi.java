package com.drozd.tester.service;

import com.drozd.tester.model.entity.Continent;
import com.drozd.tester.model.entity.Country;
import com.drozd.tester.model.entity.Link;
import com.drozd.tester.model.list.Admin1DivisionList;
import com.drozd.tester.model.list.ContinentCountriesList;
import com.drozd.tester.model.list.ContinentList;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;

public class TeleportApi {
    private static final TeleportApi teleportApi = new TeleportApi();

    public static TeleportApi getInstance() {
        return teleportApi;
    }

    private <T> T get(String uri, Class<T> destinationClass) {
        try (final CloseableHttpClient http = HttpClients.createDefault()) {
            final HttpGet query = new HttpGet(uri);
            CloseableHttpResponse response = http.execute(query);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == 200) {
                ObjectMapper om = new ObjectMapper();
                InputStream responseContent = response.getEntity().getContent();
                return om.readValue(responseContent, destinationClass);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ContinentList getContinentList() {
        return get("https://api.teleport.org/api/continents/", ContinentList.class);
    }

    public Continent getContinent(Link link) {
        return getContinent(link.getHref());
    }

    public Continent getContinent(String continentHyperlink) {
        return get(continentHyperlink, Continent.class);
    }

    public ContinentCountriesList getContinentCountriesList(Continent continent) {
        return getContinentCountriesList(continent.getCountriesListLink().getHref());
    }

    public ContinentCountriesList getContinentCountriesList(Link continentCountriesListLink) {
        return getContinentCountriesList(continentCountriesListLink.getHref());
    }

    public ContinentCountriesList getContinentCountriesList(String continentCountriesListHyperlink) {
        return get(continentCountriesListHyperlink, ContinentCountriesList.class);
    }

    public Country getCountry(Link countryLink) {
        return getCountry(countryLink.getHref());
    }

    public Country getCountry(String countryHyperlink) {
        return get(countryHyperlink, Country.class);
    }

    public Admin1DivisionList getCountryAdmin1Divisions(Country country) {
        return getCountryAdmin1Divisions(country.getAdmin1DivisionsListLink());
    }

    public Admin1DivisionList getCountryAdmin1Divisions(Link admin1DivisionsLink) {
        return getCountryAdmin1Divisions(admin1DivisionsLink.getHref());
    }

    public Admin1DivisionList getCountryAdmin1Divisions(String admin1DivisionsHyperlink) {
        return get(admin1DivisionsHyperlink, Admin1DivisionList.class);
    }

    public ContinentCountriesList getCountries(){
        return get("https://api.teleport.org/api/countries/", ContinentCountriesList.class);
    }
}
