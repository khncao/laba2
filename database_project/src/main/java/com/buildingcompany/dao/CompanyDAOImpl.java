package com.buildingcompany.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.buildingcompany.entities.Address;
import com.buildingcompany.entities.Company;
import com.buildingcompany.services.IConnectionPool;

public class CompanyDAOImpl implements CompanyDAO {
    private IConnectionPool connectionPool;
    private AddressDAO addressDAO;
    
    public CompanyDAOImpl(IConnectionPool connectionPool, AddressDAO addressDAO) {
        this.connectionPool = connectionPool;
        this.addressDAO = addressDAO;
    }

    public List<Company> getCompaniesByCountryIndustry(String countryName, String industry) {
        List<Company> companies = new ArrayList<>();
        String query = "SELECT address_id, id, name, industry.name\n"
            + "FROM company JOIN industry ON company.industry_id = industry.id\n"
                + "JOIN address ON company.address_id = address.id\n"
                + "JOIN country ON address.country_id = country.id\n"
            + "WHERE country.name = ? AND industry.name = ?;";
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, countryName);
                statement.setString(2, industry);
                ResultSet rs = statement.executeQuery();
                while(rs.next()) {
                    int addressId = rs.getInt("address_id");
                    Address address = addressDAO.getAddressById(addressId);
                    // TODO(khncao): check if address id cached, else query
                    Company company = new Company(
                        rs.getInt("id"), 
                        rs.getString("name"), 
                        address, 
                        rs.getString("industry.name"));
                    companies.add(company);
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        } finally {
            connectionPool.freeConnection(conn);
        }
        return companies;
    }
}
