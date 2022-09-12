package com.buildingcompany.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.buildingcompany.entities.Address;
import com.buildingcompany.services.IConnectionPool;

public class AddressDAOImpl implements AddressDAO {
    private IConnectionPool connectionPool;
    
    public AddressDAOImpl(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    // TODO(khncao): maybe can further parameterize or use conditional string building to avoid so much similar code
    public Address getAddressById(int primaryKey) {
        Address address = null;
        String query = "SELECT address.id, line_1, line_2, line_3, country.name, city.name, zipcode\n" 
            + "FROM address JOIN country ON address.country_id = country.id\n"
            + "             JOIN city ON address.city_id = city.id\n"
            + "WHERE id = ?;";
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, primaryKey);
                ResultSet rs = statement.executeQuery();
                if(rs.next()) {
                    address = parseAddressAllColumnsJoinName(rs);
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        } finally {
            connectionPool.freeConnection(conn);
        }
        return address;
    }

    public List<Address> getAddressesByCity(String cityName, String countryName) {
        ArrayList<Address> addresses = new ArrayList<>();
        String query = "SELECT address.id, line_1, line_2, line_3, country.name, city.name, zipcode\n" 
            + "FROM address JOIN country ON address.country_id = country.id\n"
            + "             JOIN city ON address.city_id = city.id\n"
            + "WHERE country.name = ? AND city.name = ?;";
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, countryName);
                statement.setString(2, cityName);
                ResultSet rs = statement.executeQuery();
                while(rs.next()) {
                    Address address = parseAddressAllColumnsJoinName(rs);
                    addresses.add(address);
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        } finally {
            connectionPool.freeConnection(conn);
        }
        return addresses;
    }

    public List<Address> getAddressesByCountry(String countryName) {
        ArrayList<Address> addresses = new ArrayList<>();
        String query = "SELECT address.id, line_1, line_2, line_3, country.name, city.name, zipcode\n" 
            + "FROM address JOIN country ON address.country_id = country.id\n"
            + "             JOIN city ON address.city_id = city.id\n"
            + "WHERE country.name = ?;";
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, countryName);
                ResultSet rs = statement.executeQuery();
                while(rs.next()) {
                    Address address = parseAddressAllColumnsJoinName(rs);
                    addresses.add(address);
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
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
            System.out.println(e.toString());
        }
        return address;
    }
}
