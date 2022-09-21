package com.buildingcompany.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.buildingcompany.entities.BuildingType;
import com.buildingcompany.entities.BuildingTypeAmounts;
import com.buildingcompany.entities.Material;
import com.buildingcompany.entities.Tool;
import com.buildingcompany.services.IConnectionPool;

public class BuildingTypeDAOImpl implements BuildingTypeDAO {
    private static Logger logger = LogManager.getLogger(BuildingTypeDAOImpl.class);
    private IConnectionPool connectionPool;
    private MaterialDAO materialDAO;
    private ToolDAO toolDAO;

    public BuildingTypeDAOImpl(IConnectionPool connectionPool, MaterialDAO materialDAO, ToolDAO toolDAO) {
        this.connectionPool = connectionPool;
        this.materialDAO = materialDAO;
        this.toolDAO = toolDAO;
    }

    /**
     * Created for build estimate view to get BuildingType choicebox options. 
     * @return collection of BuildingType String names for GUI choice display
     */
    public int getBuildingTypeChoiceNames(List<String> buildingTypeNames) {
        buildingTypeNames.clear();
        String query = "SELECT DISTINCT name FROM building_type;";
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                ResultSet rs = statement.executeQuery();
                while(rs.next()) {
                    buildingTypeNames.add(rs.getString("name"));
                }
            } catch (SQLException e) {
                logger.error(e.toString());
            }
        } finally {
            connectionPool.freeConnection(conn);
        }
        return buildingTypeNames.size();
    }

    public BuildingType getBuildingType(String bldgTypeName) {
        BuildingType buildingType = null;
        String query = "SELECT id, name, base_cost, min_foundation_sqrmeters, max_foundation_sqrmeters\n"
            + "FROM building_type\n"
            + "WHERE name = ? LIMIT 1;";
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, bldgTypeName);
                ResultSet rs = statement.executeQuery();
                if(rs.next()) {
                    buildingType = parseBuildingTypeAllColumns(rs);
                }
            } catch (SQLException e) {
                logger.error(e.toString());
            }
        } finally {
            connectionPool.freeConnection(conn);
        }
        return buildingType;
    }

    public BuildingType getBuildingTypeRequirements(BuildingType buildingType) {
        if(buildingType == null || buildingType.getId() < 1) {
            logger.error("getBuildingTypeRequirements buildingType null or not initialized");
            return null;
        }
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            getMaterialRequirements(conn, buildingType);
            getToolRequirements(conn, buildingType);
            getLaborRequirements(conn, buildingType);
        } finally {
            connectionPool.freeConnection(conn);
        }
        return buildingType;
    }

    private void getMaterialRequirements(Connection conn, BuildingType buildingType) {
        String query = "SELECT material_id, foundation_amount_per_sqrmeter, amount_per_sqrmeter\n"
            + "FROM building_type_material_req\n"
            + "WHERE building_type_id = ?;";
        try(PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, buildingType.getId());
            ResultSet rs = statement.executeQuery();
            buildingType.getRequiredMaterialAmounts().clear();
            while(rs.next()) {
                int materialId = rs.getInt("material_id");
                Material material = materialDAO.getMaterialById(materialId, true);
                BuildingTypeAmounts<Material> reqMats = new BuildingTypeAmounts<Material>(
                    material,
                    rs.getBigDecimal("foundation_amount_per_sqrmeter"), 
                    rs.getBigDecimal("amount_per_sqrmeter"), 
                    new BigDecimal(0f)
                );
                buildingType.getRequiredMaterialAmounts().add(reqMats);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }
    }

    private void getToolRequirements(Connection conn, BuildingType buildingType) {
        String query = "SELECT tool_id, foundation_hours_of_rental_per_sqrmeter, hours_of_rental_per_sqrmeter\n"
            + "FROM building_type_tool_req\n"
            + "WHERE building_type_id = ?;";
        try(PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, buildingType.getId());
            ResultSet rs = statement.executeQuery();
            buildingType.getRequiredToolRentalHours().clear();
            while(rs.next()) {
                int toolId = rs.getInt("tool_id");
                Tool tool = toolDAO.getToolById(toolId, true);
                BuildingTypeAmounts<Tool> reqTools = new BuildingTypeAmounts<Tool>(
                    tool,
                    rs.getBigDecimal("foundation_hours_of_rental_per_sqrmeter"),
                    rs.getBigDecimal("hours_of_rental_per_sqrmeter"),
                    new BigDecimal(0f)
                );
                buildingType.getRequiredToolRentalHours().add(reqTools);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }
    }

    private void getLaborRequirements(Connection conn, BuildingType buildingType) {
        String query = "SELECT employee_role.name, foundation_hours_of_work_per_sqrmeter, hours_of_work_per_sqrmeter, base_hours_of_work\n"
            + "FROM building_type_labor_req JOIN employee_role ON building_type_labor_req.employee_role_id = employee_role.id\n"
            + "WHERE building_type_id = ?;";
        try(PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, buildingType.getId());
            ResultSet rs = statement.executeQuery();
            buildingType.getRequiredLaborRoleHours().clear();
            while(rs.next()) {
                BuildingTypeAmounts<String> reqLabor = new BuildingTypeAmounts<String>(
                    rs.getString("employee_role.name"), 
                    rs.getBigDecimal("foundation_hours_of_work_per_sqrmeter"), 
                    rs.getBigDecimal("hours_of_work_per_sqrmeter"), 
                    rs.getBigDecimal("base_hours_of_work")
                );
                buildingType.getRequiredLaborRoleHours().add(reqLabor);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }
    }

    private BuildingType parseBuildingTypeAllColumns(ResultSet rs) {
        BuildingType buildingType = null;
        try {
            buildingType = new BuildingType(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getBigDecimal("base_cost"),
                rs.getBigDecimal("min_foundation_sqrmeters"),
                rs.getBigDecimal("max_foundation_sqrmeters")
            );
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return buildingType;
    }
}
