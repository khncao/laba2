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

import com.buildingcompany.entities.Tool;
import com.buildingcompany.services.IConnectionPool;

public class ToolDAOImpl implements ToolDAO {
    private static Logger logger = LogManager.getLogger(ToolDAOImpl.class);
    private IConnectionPool connectionPool;
    private final String ALL_COLS = " name, capacity_cubicmeters, max_load_kg, weight_kg ";
    private final String SELECT_COUNTRY_AVG_COST_NAME_BY_TOOL_ID = "SELECT country.name, avg_daily_rental_rate\n" 
    + "FROM country_tool_cost JOIN country ON country_tool_cost.country_id = country.id\n"
    + "WHERE country_tool_cost.tool_id = ?;";
    
    public ToolDAOImpl(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Tool getToolById(int primaryKey, boolean populateCountryAvgCost) {
        Tool tool = null;
        String query = "SELECT " + ALL_COLS + " FROM tool WHERE id = ?;";
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            ResultSet rs = null;
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, primaryKey);
                rs = statement.executeQuery();
                if(rs.next()) {
                    tool = new Tool(primaryKey, 
                        rs.getString("name"), 
                        rs.getBigDecimal("capacity_cubicmeters"), 
                        rs.getBigDecimal("max_load_kg"), 
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
        if(populateCountryAvgCost && tool != null) {
            getToolCountryAvgCost(tool);
        }
        return tool;
    }

    public Tool getToolCountryAvgCost(Tool tool) {
        String query = SELECT_COUNTRY_AVG_COST_NAME_BY_TOOL_ID;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            ResultSet rs = null;
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, tool.getId());
                rs = statement.executeQuery();
                tool.getPerCountryAvgCostPerRentalHour().clear();
                while(rs.next()) {
                    Map.Entry<String, BigDecimal> entry = new AbstractMap.SimpleEntry<>(
                        rs.getString("country.name"),
                        rs.getBigDecimal("avg_daily_rental_rate")
                    );
                    tool.getPerCountryAvgCostPerRentalHour().add(entry);
                }
            } finally {
                if(rs != null) rs.close();
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            connectionPool.freeConnection(conn);
        }
        return tool;
    }
}
