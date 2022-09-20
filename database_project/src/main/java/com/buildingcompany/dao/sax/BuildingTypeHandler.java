package com.buildingcompany.dao.sax;

import java.math.BigDecimal;
import java.util.Map;

import com.buildingcompany.entities.BuildingType;

public class BuildingTypeHandler extends GenericCollectionHandler<BuildingType> {
    public BuildingTypeHandler() {
        super(BuildingType.class);
    }

    public void processElement(Map<String, String> map) {
        BuildingType buildingType = new BuildingType(
            Integer.parseInt(map.get("id")), 
            map.get("name"), 
            new BigDecimal(map.getOrDefault("baseCost", "0")), 
            new BigDecimal(map.getOrDefault("minFoundationSqrMeters", "0")), 
            new BigDecimal(map.getOrDefault("maxFoundationSqrMeters", "0"))
        );
        results.add(buildingType);
    }
}
