package com.buildingcompany.services;

import java.math.BigDecimal;
import java.text.NumberFormat;

import com.buildingcompany.entities.Address;
import com.buildingcompany.entities.BuildingType;

public class CalculateBuildCostService implements ICalculateBuildCost {
    public float calculate(StringBuilder calcLog, Address address, BuildingType buildingType, float foundationSqrMeters, int numFloors) {
        float sqrMeters = foundationSqrMeters * numFloors;

        BigDecimal buildingBaseCost = buildingType.getBaseCost();
        BigDecimal foundationCostPerSqrMeter = BigDecimal.ZERO;
        BigDecimal costPerSqrMeter = BigDecimal.ZERO;
        
        // TODO(khncao): to stream
        for(var m : buildingType.getRequiredMaterialAmounts()) {
            var material = m.getObject();
            var countryAvgCost = material.getPerCountryAvgCostPerUnit()
                .stream()
                .filter(entry -> entry.getKey().trim().equalsIgnoreCase(
                                    address.getCountry().trim()))
                .findAny().orElse(null);
            if(countryAvgCost != null) {
                BigDecimal cost = countryAvgCost.getValue();
                buildingBaseCost = buildingBaseCost.add(
                    m.getAmountBase().multiply(cost)
                );
                foundationCostPerSqrMeter = foundationCostPerSqrMeter.add(
                    m.getFoundationAmountPerProjectSqrMeter().multiply(cost)
                );
                costPerSqrMeter = costPerSqrMeter.add(
                    m.getAmountPerProjectSqrMeter().multiply(cost)
                );
            }
        }
        // TODO(khncao): add tool and labor costs
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        calcLog.append(
            "~ base project cost: " + currencyFormat.format(baseCostUsd)
            + "\n~ building type cost: " + currencyFormat.format(buildingBaseCost)
            + "\n~ foundation cost/m^2: " + currencyFormat.format(foundationCostPerSqrMeter)
            + "\n~ cost/m^2: " + currencyFormat.format(costPerSqrMeter));

        return baseCostUsd + buildingBaseCost.floatValue()
        + foundationCostPerSqrMeter.floatValue() * foundationSqrMeters
        + costPerSqrMeter.floatValue() * sqrMeters;
    }
}
