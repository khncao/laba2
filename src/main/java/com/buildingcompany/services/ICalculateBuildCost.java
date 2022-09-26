package com.buildingcompany.services;

import com.buildingcompany.entities.Address;
import com.buildingcompany.entities.BuildingType;

public interface ICalculateBuildCost {
    final static float baseCostUsd = 500f;
    float calculate(StringBuilder calcLog, Address address, BuildingType buildingType, float foundationSqrMeters, int numFloors);
}
