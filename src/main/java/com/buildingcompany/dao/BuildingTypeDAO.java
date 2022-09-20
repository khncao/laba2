package com.buildingcompany.dao;

import java.util.List;

import com.buildingcompany.entities.BuildingType;

public interface BuildingTypeDAO {
    int getBuildingTypeChoiceNames(List<String> buildingTypeNames);
    BuildingType getBuildingType(String bldgTypeName);

    BuildingType getMaterialRequirements(BuildingType buildingType);
    BuildingType getToolRequirements(BuildingType buildingType);
    BuildingType getLaborRequirements(BuildingType buildingType);
}
