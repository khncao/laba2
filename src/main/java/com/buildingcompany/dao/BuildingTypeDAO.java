package com.buildingcompany.dao;

import java.util.List;

import com.buildingcompany.entities.BuildingType;

public interface BuildingTypeDAO {
    /**
     * @param buildingTypeNames inplace modified list. Will empty and repop
     * @return number of choices/rows found
     */
    int getBuildingTypeChoiceNames(List<String> buildingTypeNames);
    BuildingType getBuildingType(String bldgTypeName);

    BuildingType getMaterialRequirements(BuildingType buildingType);
    BuildingType getToolRequirements(BuildingType buildingType);
    BuildingType getLaborRequirements(BuildingType buildingType);
}
