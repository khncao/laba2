package com.buildingcompany.services;

import java.util.List;

import com.buildingcompany.entities.BuildingType;

public interface ICalculationDataCollector {
    BuildingType getBuildingType(String buildingTypeName);
    int getBuildingTypeChoiceNames(List<String> buildingTypeNames);
    BuildingType getMaterialRequirements(BuildingType buildingType);
    BuildingType getToolRequirements(BuildingType buildingType);
}
