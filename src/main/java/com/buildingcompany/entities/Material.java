package com.buildingcompany.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.buildingcompany.utility.KeyValuePair;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "Material")
public class Material {
    private int id;
    private String name;
    private BigDecimal lengthMeters;
    private BigDecimal widthMeters;
    private BigDecimal heightMeters;
    private BigDecimal weightKg;
    private List<KeyValuePair<String, BigDecimal>> perCountryAvgCostPerUnit;

    public Material() {
    }

    public Material(int id, String name, BigDecimal lengthMeters, BigDecimal widthMeters, BigDecimal heightMeters,
            BigDecimal weightKg) {
        this.id = id;
        this.name = name;
        this.lengthMeters = lengthMeters;
        this.widthMeters = widthMeters;
        this.heightMeters = heightMeters;
        this.weightKg = weightKg;
        this.perCountryAvgCostPerUnit = new ArrayList<>();
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

    public BigDecimal getLengthMeters() {
        return lengthMeters;
    }

    @XmlElement
    public void setLengthMeters(BigDecimal lengthMeters) {
        this.lengthMeters = lengthMeters;
    }

    public BigDecimal getWidthMeters() {
        return widthMeters;
    }

    @XmlElement
    public void setWidthMeters(BigDecimal widthMeters) {
        this.widthMeters = widthMeters;
    }

    public BigDecimal getHeightMeters() {
        return heightMeters;
    }

    @XmlElement
    public void setHeightMeters(BigDecimal heightMeters) {
        this.heightMeters = heightMeters;
    }

    public BigDecimal getWeightKg() {
        return weightKg;
    }

    @XmlElement
    public void setWeightKg(BigDecimal weightKg) {
        this.weightKg = weightKg;
    }

    public List<KeyValuePair<String, BigDecimal>> getPerCountryAvgCostPerUnit() {
        return perCountryAvgCostPerUnit;
    }

    @XmlTransient
    public void setPerCountryAvgCostPerUnit(List<KeyValuePair<String, BigDecimal>> perCountryAvgCost) {
        this.perCountryAvgCostPerUnit = perCountryAvgCost;
    }

    @Override
    public String toString() {
        return "Material [heightMeters=" + heightMeters + ", id=" + id + ", lengthMeters=" + lengthMeters + ", name="
                + name + ", weightKg=" + weightKg
                + ", widthMeters=" + widthMeters
                + ", perCountryAvgCostPerUnit=" + perCountryAvgCostPerUnit + "]";
    }

}
