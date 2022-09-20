package com.buildingcompany.dao.sax;

import java.math.BigDecimal;
import java.util.Map;

import com.buildingcompany.entities.Material;

public class MaterialHandler extends GenericCollectionHandler<Material> {
    public MaterialHandler() {
        super(Material.class);
    }

    public void processElement(Map<String, String> map) {
        Material material = new Material(
            Integer.parseInt(map.get("id")), 
            map.get("name"), 
            new BigDecimal(map.getOrDefault("lengthMeters", "0")), 
            new BigDecimal(map.getOrDefault("widthMeters", "0")), 
            new BigDecimal(map.getOrDefault("heightMeters", "0")), 
            new BigDecimal(map.getOrDefault("weightKg", "0"))
        );
        results.add(material);
    }
}
