package com.buildingcompany.controllers;

import java.text.NumberFormat;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.buildingcompany.dao.BuildingTypeDAO;
import com.buildingcompany.entities.Address;
import com.buildingcompany.entities.BuildingType;
import com.buildingcompany.services.CalculateBuildCostService;
import com.buildingcompany.services.CalculateBuildTimeService;
import com.buildingcompany.services.DAOFactory;
import com.buildingcompany.services.ICalculateBuildCost;
import com.buildingcompany.services.ICalculateBuildTime;

public class BuildEstimateController {
    private static Logger logger = LogManager.getLogger(BuildEstimateController.class);
    private ICalculateBuildTime calculateBuildTime;
    private ICalculateBuildCost calculateBuildCost;
    private BuildingTypeDAO buildingTypeDAO;

    public BuildEstimateController() {
        buildingTypeDAO = DAOFactory.<BuildingTypeDAO>getDaoImpl(BuildingType.class, "mybatis");
        calculateBuildCost = new CalculateBuildCostService();
        calculateBuildTime = new CalculateBuildTimeService();
    }

    public String runCalculation(Address address, String buildingTypeName, float foundationSqrMeters, int numFloors) {
        logger.info("Calculation Input: [" + address.toString() + "], [Type: " + buildingTypeName + "], [M^2: " + foundationSqrMeters + "], [Floors: " + numFloors + ']');
        
        BuildingType buildingType = buildingTypeDAO.getBuildingType(buildingTypeName);
        buildingTypeDAO.getMaterialRequirements(buildingType);
        buildingTypeDAO.getToolRequirements(buildingType);
        // buildingTypeDAO.getLaborRequirements(buildingType);
        
        StringBuilder timeCalcLog = new StringBuilder();
        StringBuilder costCalcLog = new StringBuilder();
        float totalHours = calculateBuildTime.calculate(timeCalcLog, address, buildingType, foundationSqrMeters, numFloors);
        float totalCost = calculateBuildCost.calculate(costCalcLog, address, buildingType, foundationSqrMeters, numFloors);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        int days = (int)(totalHours / 24);
        int hours = (int)(totalHours % 24);
        String output = "Approx. Cost: " + currencyFormat.format(totalCost) 
            + "\n" + costCalcLog.toString()
            + "\nApprox. Time: " + days + " days and " + hours + " hours"
            + "\n" + timeCalcLog.toString();
        return output;
    }

    public void testPrintReqResources(String buildingTypeName) {
        BuildingType buildingType = buildingTypeDAO.getBuildingType(buildingTypeName);
        buildingTypeDAO.getMaterialRequirements(buildingType);
        buildingTypeDAO.getToolRequirements(buildingType);
        buildingTypeDAO.getLaborRequirements(buildingType);
        logger.info("Requirements: ");
        logger.info("Mats:");
        buildingType.getRequiredMaterialAmounts().stream()
            .forEach((var d)->{logger.info(d);});
        logger.info("Tools:");
        buildingType.getRequiredToolRentalHours().stream()
            .forEach((var d)->{logger.info(d);});
        logger.info("Labor:");
        buildingType.getRequiredLaborRoleHours().stream()
            .forEach((var d)->{logger.info(d);});
    }

    public int updateBuildingTypeChoices(List<String> buildingTypeNames) {
        return buildingTypeDAO.getBuildingTypeChoiceNames(buildingTypeNames);
    }
}
