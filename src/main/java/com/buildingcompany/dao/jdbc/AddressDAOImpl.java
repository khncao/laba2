package com.buildingcompany.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.buildingcompany.dao.AddressDAO;
import com.buildingcompany.dao.DAO;
import com.buildingcompany.entities.Address;
import com.buildingcompany.services.IConnectionPool;

public class AddressDAOImpl extends DAOImpl implements AddressDAO {
    private static Logger logger = LogManager.getLogger(AddressDAOImpl.class);
    private final String ALL_COLS = "address.id,line_1,line_2,line_3,country_id,city_id,zipcode";
    private final String SELECT_ALL_COLS_NAMES_JOINED = "SELECT address.id, line_1, line_2, line_3, country.name, city.name, zipcode " 
    + "FROM address JOIN country ON address.country_id = country.id "
    + "             JOIN city ON address.city_id = city.id ";
    private final String UPDATE_ALL_COLS_FORMAT = "UPDATE address SET address.id=%s,line_1=%s,line_2=%s,line_3=%s,country_id=%s,city_id=%s,zipcode=%s WHERE address.id = %s";
    private final String INSERT_ALL_COLS_FORMAT = "INSERT INTO address(" + ALL_COLS + ") VALUES (%s,%s,%s,%s,%s,%s,%s)";
    private final String DELETE_BY_ID_FORMAT = "DELETE FROM address WHERE address.id = %s";

    public AddressDAOImpl() {
        super();
    }
    
    public AddressDAOImpl(IConnectionPool connectionPool) {
        super(connectionPool);
    }

    public Address getAddressById(int primaryKey) {
        Address address = null;
        String query = SELECT_ALL_COLS_NAMES_JOINED + "WHERE id = ?;";
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            ResultSet rs = null;
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, primaryKey);
                rs = statement.executeQuery();
                if(rs.next()) {
                    address = parseAddressAllColumnsJoinName(rs);
                }
            } finally {
                if(rs != null) rs.close();
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            connectionPool.freeConnection(conn);
        }
        return address;
    }

    public List<Address> getAddressesByCity(String cityName, String countryName) {
        ArrayList<Address> addresses = new ArrayList<>();
        String query = SELECT_ALL_COLS_NAMES_JOINED
            + "WHERE country.name = ? AND city.name = ?;";
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            ResultSet rs = null;
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, countryName);
                statement.setString(2, cityName);
                rs = statement.executeQuery();
                while(rs.next()) {
                    Address address = parseAddressAllColumnsJoinName(rs);
                    addresses.add(address);
                }
            } finally { 
                if(rs != null) rs.close();
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            connectionPool.freeConnection(conn);
        }
        return addresses;
    }

    public List<Address> getAddressesByCountry(String countryName) {
        ArrayList<Address> addresses = new ArrayList<>();
        String query = SELECT_ALL_COLS_NAMES_JOINED + "WHERE country.name = ?;";
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            ResultSet rs = null;
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, countryName);
                rs = statement.executeQuery();
                while(rs.next()) {
                    Address address = parseAddressAllColumnsJoinName(rs);
                    addresses.add(address);
                }
            } finally {
                if(rs != null) rs.close();
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            connectionPool.freeConnection(conn);
        }
        return addresses;
    }

    private Address parseAddressAllColumnsJoinName(ResultSet rs) {
        Address address = null;
        try {
            address = new Address(
                rs.getInt("address.id"),
                rs.getString("line_1"),
                rs.getString("line_2"),
                rs.getString("line_3"),
                rs.getString("country.name"),
                rs.getString("city.name"),
                rs.getString("zipcode")
            );
        } catch(SQLException e) {
            logger.error(e.toString());
        }
        return address;
    }

    public void create(Address t) {
        String query = String.format(INSERT_ALL_COLS_FORMAT + ";", 
            t.getId(), 
            t.getLine1(), 
            t.getLine2(), 
            t.getLine3(), 
            DAO.generateGetIdFromName("country", t.getCountry()), 
            DAO.generateGetIdFromName("city", t.getCity()), 
            t.getZipCode());
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                statement.execute();
            } catch (SQLException e) {
                logger.error(e.toString());
            }
        } finally {
            connectionPool.freeConnection(conn);
        }
    }

    public List<Address> readAll() {
        ArrayList<Address> addresses = new ArrayList<>();
        String query = SELECT_ALL_COLS_NAMES_JOINED + ";";
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            ResultSet rs = null;
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                rs = statement.executeQuery();
                while(rs.next()) {
                    Address address = parseAddressAllColumnsJoinName(rs);
                    addresses.add(address);
                }
            } finally {
                if(rs != null) rs.close();
            }
        } catch (SQLException e) {
                logger.error(e.toString());
        } finally {
            connectionPool.freeConnection(conn);
        }
        return addresses;
    }

    public void update(Address t) {
        String query = String.format(UPDATE_ALL_COLS_FORMAT + ";", 
            t.getId(), 
            t.getLine1(), 
            t.getLine2(), 
            t.getLine3(), 
            DAO.generateGetIdFromName("country", t.getCountry()), 
            DAO.generateGetIdFromName("city", t.getCity()), 
            t.getZipCode(),
            t.getId());
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                int count = statement.executeUpdate();
                logger.info("Updated records: " + count);
            } catch (SQLException e) {
                logger.error(e.toString());
            }
        } finally {
            connectionPool.freeConnection(conn);
        }
    }
    
    public void delete(Address t) {
        String query = String.format(DELETE_BY_ID_FORMAT + ";", t.getId());
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                int count = statement.executeUpdate();
                logger.info("Deleted record count: " + count);
            } catch (SQLException e) {
                logger.error(e.toString());
            }
        } finally {
            connectionPool.freeConnection(conn);
        }
    }
}
