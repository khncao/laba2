package com.buildingcompany.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BuildingType")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BuildingType {
    @JsonProperty
    private int id;
    @JsonProperty
    private String name;
    @JsonProperty
    private BigDecimal baseCost;
    @JsonProperty
    private BigDecimal minFoundationSqrMeters;
    @JsonProperty
    private BigDecimal maxFoundationSqrMeters;
    @JsonProperty
    private List<BuildingTypeAmounts<Material>> requiredMaterialAmounts;
    @JsonProperty
    private List<BuildingTypeAmounts<Tool>> requiredToolRentalHours;
    @JsonProperty
    private List<BuildingTypeAmounts<String>> requiredLaborRoleHours;

    public BuildingType() {
    }

    public BuildingType(int id, String name, BigDecimal baseCost, BigDecimal minFoundationSqrMeters,
            BigDecimal maxFoundationSqrMeters) {
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

    public BigDecimal getBaseCost() {
        return baseCost;
    }

    @XmlElement
    public void setBaseCost(BigDecimal baseCost) {
        this.baseCost = baseCost;
    }

    public BigDecimal getMinFoundationSqrMeters() {
        return minFoundationSqrMeters;
    }

    @XmlElement
    public void setMinFoundationSqrMeters(BigDecimal minFoundationSqrMeters) {
        this.minFoundationSqrMeters = minFoundationSqrMeters;
    }

    public BigDecimal getMaxFoundationSqrMeters() {
        return maxFoundationSqrMeters;
    }

    @XmlElement
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
