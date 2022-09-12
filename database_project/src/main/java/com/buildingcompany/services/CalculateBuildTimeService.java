package com.buildingcompany.services;

import com.buildingcompany.entities.Address;
import com.buildingcompany.entities.BuildingType;

public class CalculateBuildTimeService implements ICalculateBuildTime {
    private final static float baseTimeHours = 40f;

    public float calculate(StringBuilder calcLog, Address address, BuildingType buildingType, float foundationSqrMeters, int numFloors) {
        float sqrMeters = foundationSqrMeters * numFloors;

        float baseWorkHours = 0f;
        float foundationHoursPerSqrMeter = 0f;
        float hoursPerSqrMeter = 0f;

        for(var t : buildingType.getRequiredToolRentalHours()) {
            baseWorkHours += t.getAmountBase().floatValue();
            foundationHoursPerSqrMeter += t.getFoundationAmountPerProjectSqrMeter().floatValue();
            hoursPerSqrMeter += t.getAmountPerProjectSqrMeter().floatValue();
        }

        // TODO(khncao): add labor hours; should ideally consider overlap
        calcLog.append(
            "~ base project hours: " + baseTimeHours
            + "\n~ type labor hours: " + baseWorkHours
            + "\n~ foundation hours/m^2: " + foundationHoursPerSqrMeter
            + "\n~ hours/m^2: " + hoursPerSqrMeter);

        return baseTimeHours + baseWorkHours
            + foundationHoursPerSqrMeter * foundationSqrMeters
            + hoursPerSqrMeter * sqrMeters;
    }
}
