package com.drozd.tester.model.list;


import com.drozd.tester.model.entity.Link;
import com.drozd.tester.service.deserializer.Admin1DivisionListDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Arrays;

@JsonDeserialize(using = Admin1DivisionListDeserializer.class)
public class Admin1DivisionList {
    @JsonProperty("count")
    Integer count;
    @JsonProperty("self")
    Link self;
    @JsonProperty("a1:country")
    Link country;
    @JsonProperty("a1:items")
    Link[] adminDivisions;

    public Admin1DivisionList() {
    }

    public Admin1DivisionList(Integer count, Link self, Link country, Link[] adminDivisions) {
        this.count = count;
        this.self = self;
        this.country = country;
        this.adminDivisions = adminDivisions;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Link getSelf() {
        return self;
    }

    public void setSelf(Link self) {
        this.self = self;
    }

    public Link getCountry() {
        return country;
    }

    public void setCountry(Link country) {
        this.country = country;
    }

    public Link[] getAdminDivisions() {
        return adminDivisions;
    }

    public void setAdminDivisions(Link[] adminDivisions) {
        this.adminDivisions = adminDivisions;
    }

    @Override
    public String toString() {
        return "Admin1DivisionList{" +
                "count=" + count +
                ", self=" + self +
                ", country=" + country +
                ", adminDivisions=" + Arrays.toString(adminDivisions) +
                '}';
    }
}
