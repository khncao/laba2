package com.buildingcompany.entities;

public class Address {
    private int id;
    private String line1;
    private String line2;
    private String line3;
    /**
     * FK country table, country.name
     */
    private String country;

    /**
     * FK city table, city.name
     */
    private String city;
    private String zipCode;
    
    public Address(int id, String line1, String line2, String line3, String country, String city, String zipCode) {
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

    public void setId(int id) {
        this.id = id;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Address [city=" + city + ", country=" + country + ", line1=" + line1 + ", line2=" + line2 + ", line3="
                + line3 + ", zipCode=" + zipCode + "]";
    }
}
