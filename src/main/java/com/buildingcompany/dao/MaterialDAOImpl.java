package com.buildingcompany.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.buildingcompany.entities.Material;
import com.buildingcompany.services.IConnectionPool;

public class MaterialDAOImpl implements MaterialDAO {
    private static Logger logger = LogManager.getLogger(MaterialDAOImpl.class);
    private IConnectionPool connectionPool;
    private final String SELECT_ALL_COLS = "SELECT name, length_meters, width_meters, height_meters, weight_kg\n" 
    + "FROM material\n";
    private final String SELECT_COUNTRY_AVG_COST_NAME_BY_MAT_ID = "SELECT country.name, avg_cost_per_amount\n" 
    + "FROM country_material_cost JOIN country ON country_material_cost.country_id = country.id\n"
    + "WHERE country_material_cost.material_id = ?;";
    
    public MaterialDAOImpl(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Material getMaterialById(int primaryKey, boolean populateCountryAvgCost) {
        Material material = null;
        String query = SELECT_ALL_COLS + "WHERE id = ?;";
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            ResultSet rs = null;
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, primaryKey);
                rs = statement.executeQuery();
                if(rs.next()) {
                    material = new Material(primaryKey, 
                        rs.getString("name"), 
                        rs.getBigDecimal("length_meters"), 
                        rs.getBigDecimal("width_meters"), 
                        rs.getBigDecimal("height_meters"), 
                        rs.getBigDecimal("weight_kg"));
                }
            } finally {
                if(rs != null) rs.close();
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            connectionPool.freeConnection(conn);
        }
        if(populateCountryAvgCost && material != null) {
            getMaterialCountryAvgCost(material);
        }
        return material;
    }

    public Material getMaterialCountryAvgCost(Material material) {
        String query = SELECT_COUNTRY_AVG_COST_NAME_BY_MAT_ID;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            ResultSet rs = null;
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, material.getId());
                rs = statement.executeQuery();
                material.getPerCountryAvgCostPerUnit().clear();
                while(rs.next()) {
                    Map.Entry<String, BigDecimal> entry = new AbstractMap.SimpleEntry<>(
                        rs.getString("country.name"), 
                        rs.getBigDecimal("avg_cost_per_amount")
                    );
                    material.getPerCountryAvgCostPerUnit().add(entry);
                }
            } finally {
                if(rs != null) rs.close();
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            connectionPool.freeConnection(conn);
        }
        return material;
    }
}
