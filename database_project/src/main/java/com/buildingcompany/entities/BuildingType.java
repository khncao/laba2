package com.buildingcompany.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.buildingcompany.utility.BuildingTypeAmounts;

public class BuildingType {
    private int id;
    private String name;
    private BigDecimal baseCost;
    private BigDecimal minFoundationSqrMeters;
    private BigDecimal maxFoundationSqrMeters;
    private List<BuildingTypeAmounts<Material>> requiredMaterialAmounts;
    private List<BuildingTypeAmounts<Tool>> requiredToolRentalHours;
    private List<BuildingTypeAmounts<String>> requiredLaborRoleHours;

    public BuildingType(int id, String name, BigDecimal baseCost, BigDecimal minFoundationSqrMeters, BigDecimal maxFoundationSqrMeters) {
        this.id = id;
        this.name = name;
        this.baseCost = baseCost;
        this.minFoundationSqrMeters = minFoundationSqrMeters;
        this.maxFoundationSqrMeters = maxFoundationSqrMeters;
        this.requiredMaterialAmounts = new ArrayList<>();
        this.requiredToolRentalHours = new ArrayList<>();
        this.requiredLaborRoleHours = new ArrayList<>();
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

    public BigDecimal getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(BigDecimal baseCost) {
        this.baseCost = baseCost;
    }

    public BigDecimal getMinFoundationSqrMeters() {
        return minFoundationSqrMeters;
    }

    public void setMinFoundationSqrMeters(BigDecimal minFoundationSqrMeters) {
        this.minFoundationSqrMeters = minFoundationSqrMeters;
    }

    public BigDecimal getMaxFoundationSqrMeters() {
        return maxFoundationSqrMeters;
    }

    public void setMaxFoundationSqrMeters(BigDecimal maxFoundationSqrMeters) {
        this.maxFoundationSqrMeters = maxFoundationSqrMeters;
    }

    public List<BuildingTypeAmounts<Material>> getRequiredMaterialAmounts() {
        return requiredMaterialAmounts;
    }

    public List<BuildingTypeAmounts<Tool>> getRequiredToolRentalHours() {
        return requiredToolRentalHours;
    }

    public List<BuildingTypeAmounts<String>> getRequiredLaborRoleHours() {
        return requiredLaborRoleHours;
    }

    @Override
    public String toString() {
        return "BuildingType [baseCost=" + baseCost + ", id=" + id + ", maxFoundationSqrMeters="
                + maxFoundationSqrMeters + ", minFoundationSqrMeters=" + minFoundationSqrMeters + ", name=" + name
                + ", requiredLaborRoleHours=" + requiredLaborRoleHours + ", requiredMaterialAmounts="
                + requiredMaterialAmounts + ", requiredToolRentalHours=" + requiredToolRentalHours + "]";
    }
}
