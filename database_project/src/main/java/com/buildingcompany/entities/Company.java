package com.buildingcompany.entities;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Company")
public class Company {
    private int id;
    private String name;

    /**
     * Address may not have proper id field if not queried from database.
     * If want to use address.id should check if value is greater than 0
     */
    private Address address;

    /**
     * String form of ideally enum type 'industry' table.
     * There's probably a better implementation, maybe wrapper around enum/table
     * ordinal
     */
    private String industry;

    public Company() {
    }

    public Company(int id, String name, Address address, String industry) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.industry = industry;
    }

    public int getId() {
        return id;
    }

    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    @XmlElement
    public void setAddress(Address address) {
        this.address = address;
    }

    public String getIndustry() {
        return industry;
    }

    @XmlElement
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @Override
    public String toString() {
        return "Company [address=" + address + ", industry=" + industry + ", name=" + name + "]";
    }
}
