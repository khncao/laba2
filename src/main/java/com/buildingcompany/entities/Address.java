package com.buildingcompany.entities;

import java.util.Date;

import com.buildingcompany.utility.adapters.XmlDateTimeAdapter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "Address")
@JsonInclude(Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Address {
    @JsonProperty
    private int id;
    @JsonProperty
    private String line1;
    @JsonProperty
    private String line2;
    @JsonProperty
    private String line3;
    /**
     * FK country table, country.name
     */
    @JsonProperty
    private String country;

    /**
     * FK city table, city.name
     */
    @JsonProperty
    private String city;
    @JsonProperty("zipcode")
    private String zipCode;
    private Date lastUpdated;

    public Address() {
    }

    public Address(int id, String line1, String line2, String line3, String country, String city, String zipCode) {
        this.id = id;
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.country = country;
        this.city = city;
        this.zipCode = zipCode;
    }

    public int getId() {
        return id;
    }

    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public String getLine1() {
        return line1;
    }

    @XmlElement
    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    @XmlElement
    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    @XmlElement
    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getCountry() {
        return country;
    }

    @XmlElement
    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    @XmlElement
    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    @XmlElement
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    @XmlElement
    @XmlJavaTypeAdapter(XmlDateTimeAdapter.class)
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "Address [id=" + id + ", city=" + city + ", country=" + country + ", line1=" + line1 + ", line2=" + line2
                + ", line3="
                + line3 + ", zipCode=" + zipCode + "]";
    }
}
