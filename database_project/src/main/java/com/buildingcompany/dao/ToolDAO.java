package com.buildingcompany.dao;

import com.buildingcompany.entities.Tool;

public interface ToolDAO {
    Tool getToolById(int primaryKey, boolean populateCountryAvgCost);

    /**
     * Inplace populate list of per country costs per tool. Clears existing before running
     * @param tool
     * @return
     */
    Tool getToolCountryAvgCost(Tool tool);
}
