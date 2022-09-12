package com.buildingcompany.services;

import com.buildingcompany.entities.Address;
import com.buildingcompany.entities.BuildingType;

public interface ICalculateBuildTime {
    float calculate(StringBuilder calcLog, Address address, BuildingType buildingType, float foundationSqrMeters, int numFloors);
}
