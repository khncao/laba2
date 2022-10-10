package com.buildingcompany.services.jdbc;

import java.util.List;

import com.buildingcompany.dao.BuildingTypeDAO;
import com.buildingcompany.dao.jdbc.BuildingTypeDAOImpl;
import com.buildingcompany.entities.BuildingType;
import com.buildingcompany.services.ICalculationDataCollector;

public class CalculationDataCollectorImpl implements ICalculationDataCollector {
    private BuildingTypeDAO buildingTypeDAO;

    public CalculationDataCollectorImpl() {
        buildingTypeDAO = new BuildingTypeDAOImpl();
    }

    public BuildingType getBuildingType(String buildingTypeName) {
        return buildingTypeDAO.getBuildingType(buildingTypeName);
    }
    
    public int getBuildingTypeChoiceNames(List<String> buildingTypeNames) {
        return buildingTypeDAO.getBuildingTypeChoiceNames(buildingTypeNames);
    }

    public BuildingType getMaterialRequirements(BuildingType buildingType) {
        return buildingTypeDAO.getMaterialRequirements(buildingType);
    }

    public BuildingType getToolRequirements(BuildingType buildingType) {
        return buildingTypeDAO.getToolRequirements(buildingType);
    }
}
