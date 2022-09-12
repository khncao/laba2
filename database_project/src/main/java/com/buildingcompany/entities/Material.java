package com.buildingcompany.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Material {
    private int id;
    private String name;
    private BigDecimal lengthMeters;
    private BigDecimal widthMeters;
    private BigDecimal heightMeters;
    private BigDecimal weightKg;
    private List<Map.Entry<String, BigDecimal>> perCountryAvgCostPerUnit;

    public Material(int id, String name, BigDecimal lengthMeters, BigDecimal widthMeters, BigDecimal heightMeters, BigDecimal weightKg) {
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

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLengthMeters() {
        return lengthMeters;
    }

    public void setLengthMeters(BigDecimal lengthMeters) {
        this.lengthMeters = lengthMeters;
    }

    public BigDecimal getWidthMeters() {
        return widthMeters;
    }

    public void setWidthMeters(BigDecimal widthMeters) {
        this.widthMeters = widthMeters;
    }

    public BigDecimal getHeightMeters() {
        return heightMeters;
    }

    public void setHeightMeters(BigDecimal heightMeters) {
        this.heightMeters = heightMeters;
    }

    public BigDecimal getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(BigDecimal weightKg) {
        this.weightKg = weightKg;
    }

    public List<Map.Entry<String, BigDecimal>> getPerCountryAvgCostPerUnit() {
        return perCountryAvgCostPerUnit;
    }

    public void setPerCountryAvgCostPerUnit(List<Map.Entry<String, BigDecimal>> perCountryAvgCost) {
        this.perCountryAvgCostPerUnit = perCountryAvgCost;
    }

    @Override
    public String toString() {
        return "Material [id=" + id + ", heightMeters=" + heightMeters + ",  lengthMeters=" + lengthMeters + ", name="
                + name + ", weightKg=" + weightKg + ", widthMeters=" + widthMeters + "]";
    }
}
