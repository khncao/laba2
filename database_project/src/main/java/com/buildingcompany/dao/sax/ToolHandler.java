package com.buildingcompany.dao.sax;

import java.math.BigDecimal;
import java.util.Map;

import com.buildingcompany.entities.Tool;

public class ToolHandler extends GenericCollectionHandler<Tool> {
    public ToolHandler() {
        super(Tool.class);
    }

    public void processElement(Map<String, String> map) {
        Tool tool = new Tool(
            Integer.parseInt(map.get("id")), 
            map.get("name"), 
            new BigDecimal(map.getOrDefault("capacityCubicMeters", "0")), 
            new BigDecimal(map.getOrDefault("maxLoadKg", "0")), 
            new BigDecimal(map.getOrDefault("weightKg", "0"))
        );
        results.add(tool);
    }
}
