package com.buildingcompany.dao;

import java.util.List;

import com.buildingcompany.entities.BuildingType;

public interface BuildingTypeDAO {
    int getBuildingTypeChoiceNames(List<String> buildingTypeNames);
    BuildingType getBuildingType(String bldgTypeName);

    /**
     * Populates inplace lists of required materials, tools, labor(employee roles) amounts. Clears existing lists before running
     * @param buildingType
     * @return
     */
    BuildingType getBuildingTypeRequirements(BuildingType buildingType);
}
