package com.buildingcompany.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.buildingcompany.utility.KeyValuePair;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "Tool")
public class Tool {
    private int id;
    private String name;
    private BigDecimal capacityCubicMeters;
    private BigDecimal maxLoadKg;
    private BigDecimal weightKg;
    private List<KeyValuePair<String, BigDecimal>> perCountryAvgCostPerRentalHour;

    public Tool() {
    }

    public Tool(int id, String name, BigDecimal capacityCubicMeters, BigDecimal maxLoadKg, BigDecimal weightKg) {
        this.id = id;
        this.name = name;
        this.capacityCubicMeters = capacityCubicMeters;
        this.maxLoadKg = maxLoadKg;
        this.weightKg = weightKg;
        this.perCountryAvgCostPerRentalHour = new ArrayList<>();
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

    public BigDecimal getCapacityCubicMeters() {
        return capacityCubicMeters;
    }

    @XmlElement
    public void setCapacityCubicMeters(BigDecimal capacityCubicMeters) {
        this.capacityCubicMeters = capacityCubicMeters;
    }

    public BigDecimal getMaxLoadKg() {
        return maxLoadKg;
    }

    @XmlElement
    public void setMaxLoadKg(BigDecimal maxLoadKg) {
        this.maxLoadKg = maxLoadKg;
    }

    public BigDecimal getWeightKg() {
        return weightKg;
    }

    @XmlElement
    public void setWeightKg(BigDecimal weightKg) {
        this.weightKg = weightKg;
    }

    public List<KeyValuePair<String, BigDecimal>> getPerCountryAvgCostPerRentalHour() {
        return perCountryAvgCostPerRentalHour;
    }

    @XmlTransient
    public void setPerCountryAvgCostPerRentalHour(List<KeyValuePair<String, BigDecimal>> perCountryAvgCost) {
        this.perCountryAvgCostPerRentalHour = perCountryAvgCost;
    }

    @Override
    public String toString() {
        return "Tool [id=" + id + ", capacityCubicMeters=" + capacityCubicMeters + ", maxLoadKg=" + maxLoadKg
                + ", name=" + name + ", weightKg=" + weightKg 
                + ", perCountryAvgCostPerRentalHour=" + perCountryAvgCostPerRentalHour + "]";
    }
}
