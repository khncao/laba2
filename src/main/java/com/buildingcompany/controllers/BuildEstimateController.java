package com.buildingcompany.controllers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.buildingcompany.entities.Address;
import com.buildingcompany.entities.BuildingType;
import com.buildingcompany.services.CalculateBuildCostService;
import com.buildingcompany.services.CalculateBuildTimeService;
import com.buildingcompany.services.ICalculateBuildCost;
import com.buildingcompany.services.ICalculateBuildTime;
import com.buildingcompany.services.ICalculationDataCollector;

public class BuildEstimateController implements PropertyChangeListener {
    private static Logger logger = LogManager.getLogger(BuildEstimateController.class);
    private ICalculateBuildTime calculateBuildTime;
    private ICalculateBuildCost calculateBuildCost;
    private ICalculationDataCollector dataCollector;

    public BuildEstimateController() {
        calculateBuildCost = new CalculateBuildCostService();
        calculateBuildTime = new CalculateBuildTimeService();
        dataCollector = new com.buildingcompany.services.mybatis.CalculationDataCollectorImpl();
        // dataCollector = new com.buildingcompany.services.jdbc.CalculationDataCollectorImpl();
    }

    public String runCalculation(Address address, String buildingTypeName, float foundationSqrMeters, int numFloors) {
        logger.info("Calculation Input: [" + address.toString() + "], [Type: " + buildingTypeName + "], [M^2: " + foundationSqrMeters + "], [Floors: " + numFloors + ']');
        
        BuildingType buildingType = dataCollector.getBuildingType(buildingTypeName);
        dataCollector.getMaterialRequirements(buildingType);
        dataCollector.getToolRequirements(buildingType);
        
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

    @Override
    public void propertyChange(PropertyChangeEvent ev) {
        logger.info(this + " observed change " + ev.getSource() + " " + ev.getPropertyName() + ": " + ev.getOldValue() + " -> " + ev.getNewValue());
    }

    public int updateBuildingTypeChoices(List<String> buildingTypeNames) {
        return dataCollector.getBuildingTypeChoiceNames(buildingTypeNames);
    }
}
