package com.buildingcompany.services;

import java.math.BigDecimal;

import com.buildingcompany.entities.Address;
import com.buildingcompany.entities.BuildingType;
import com.buildingcompany.entities.Tool;
import com.buildingcompany.utility.BuildingTypeAmounts;

public class CalculateBuildTimeService implements ICalculateBuildTime {
    private final static float baseTimeHours = 40f;

    public float calculate(StringBuilder calcLog, Address address, BuildingType buildingType, float foundationSqrMeters, int numFloors) {
        float sqrMeters = foundationSqrMeters * numFloors;

        BuildingTypeAmounts<Tool> sums = buildingType.getRequiredToolRentalHours().stream()
            .reduce((a, b)->
                new BuildingTypeAmounts<Tool>(null, 
                    a.getAmountBase().add(b.getAmountBase()), 
                    a.getFoundationAmountPerProjectSqrMeter().add(b.getFoundationAmountPerProjectSqrMeter()), 
                    a.getAmountPerProjectSqrMeter().add(b.getAmountPerProjectSqrMeter())
                )
            ).orElse(new BuildingTypeAmounts<Tool>(null, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));

        // TODO(khncao): add labor hours; should ideally consider overlap
        calcLog.append(
            "~ base project hours: " + baseTimeHours
            + "\n~ type labor hours: " + sums.getAmountBase()
            + "\n~ foundation hours/m^2: " + sums.getFoundationAmountPerProjectSqrMeter()
            + "\n~ hours/m^2: " + sums.getAmountPerProjectSqrMeter());

        return baseTimeHours + sums.getAmountBase().floatValue()
            + sums.getFoundationAmountPerProjectSqrMeter().floatValue() * foundationSqrMeters
            + sums.getAmountPerProjectSqrMeter().floatValue() * sqrMeters;
    }
}
