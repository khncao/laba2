package com.buildingcompany.dao;

import com.buildingcompany.entities.Material;

public interface MaterialDAO {
    Material getMaterialById(int primaryKey, boolean populateCountryAvgCost);

    /**
     * Inplace populate list of per country costs per material. Clears existing before running
     * @param material
     * @return
     */
    Material getMaterialCountryAvgCost(Material material);
}
