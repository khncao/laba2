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
    private final String SELECT_DISTINCT_NAMES = "SELECT DISTINCT name FROM building_type;";
    private final String ALL_COLS = " id, name, base_cost, min_foundation_sqrmeters, max_foundation_sqrmeters ";
    private final String MAT_REQ_COLS = " material_id," 
        + "building_type_material_req.foundation_amount_per_sqrmeter," 
        + "building_type_material_req.amount_per_sqrmeter ";
    private final String TOOL_REQ_COLS = " tool_id,"
        + "building_type_tool_req.foundation_hours_of_rental_per_sqrmeter,"
        + "building_type_tool_req.hours_of_rental_per_sqrmeter ";
    private final String LABOR_REQ_COLS_JOIN_NAME = " employee_role.name,"
        + "building_type_labor_req.foundation_hours_of_work_per_sqrmeter,"
        + "building_type_labor_req.hours_of_work_per_sqrmeter,"
        + "building_type_labor_req.base_hours_of_work ";
    private final String JOIN_MAT_REQS_BY_BUILDING_TYPE = " JOIN building_type_material_req ON building_type_material_req.building_type_id = building_type.id ";
    private final String JOIN_TOOL_REQS_BY_BUILDING_TYPE = " JOIN building_type_tool_req ON building_type_tool_req.building_type_id = building_type.id ";
    private final String JOIN_LABOR_REQS_BY_BUILDING_TYPE = " JOIN building_type_labor_req ON building_type_labor_req.building_type_id = building_type.id ";
    private final String JOIN_LABOR_REQ_EMPLOYEE_ROLE = " JOIN employee_role ON building_type_labor_req.employee_role_id = employee_role.id ";

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
        String query = SELECT_DISTINCT_NAMES;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            ResultSet rs = null;
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                rs = statement.executeQuery();
                while(rs.next()) {
                    buildingTypeNames.add(rs.getString("name"));
                }
            } finally {
                if(rs != null) rs.close();
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            connectionPool.freeConnection(conn);
        }
        return buildingTypeNames.size();
    }

    public BuildingType getBuildingType(String bldgTypeName) {
        BuildingType buildingType = null;
        String query = "SELECT " + ALL_COLS + "FROM building_type WHERE name = ? LIMIT 1;";
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            ResultSet rs = null;
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, bldgTypeName);
                rs = statement.executeQuery();
                if(rs.next()) {
                    buildingType = parseBuildingTypeAllColumns(rs);
                }
            } finally {
                if(rs != null) rs.close();
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            connectionPool.freeConnection(conn);
        }
        return buildingType;
    }

    public BuildingType getMaterialRequirements(BuildingType buildingType) {
        String query = "SELECT " + MAT_REQ_COLS
            + "FROM building_type_material_req\n"
            + "WHERE building_type_id = ?;";
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            ResultSet rs = null;
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, buildingType.getId());
                rs = statement.executeQuery();
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
            } finally {
                if(rs != null) rs.close();
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            connectionPool.freeConnection(conn);
        }
        return buildingType;
    }

    public BuildingType getToolRequirements(BuildingType buildingType) {
        String query = "SELECT " + TOOL_REQ_COLS
            + "FROM building_type_tool_req\n"
            + "WHERE building_type_id = ?;";
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            ResultSet rs = null;
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, buildingType.getId());
                rs = statement.executeQuery();
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
            } finally {
                if(rs != null) rs.close();
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            connectionPool.freeConnection(conn);
        }
        return buildingType;
    }

    public BuildingType getLaborRequirements(BuildingType buildingType) {
        String query = "SELECT " + LABOR_REQ_COLS_JOIN_NAME
            + "FROM building_type_labor_req" + JOIN_LABOR_REQ_EMPLOYEE_ROLE
            + "WHERE building_type_id = ?;";
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            ResultSet rs = null;
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, buildingType.getId());
                rs = statement.executeQuery();
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
            } finally {
                if(rs != null) rs.close();
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            connectionPool.freeConnection(conn);
        }
        return buildingType;
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
