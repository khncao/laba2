package com.buildingcompany.mappers;

import java.util.List;

import com.buildingcompany.entities.BuildingType;

public interface BuildingTypeMapper {
    BuildingType selectBuildingType(int id);
    BuildingType selectBuildingTypeByName(String name);
    List<String> selectBuildingTypeNames();
}
