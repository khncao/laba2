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
import com.buildingcompany.dao.CompanyDAO;
import com.buildingcompany.entities.Address;
import com.buildingcompany.entities.Company;
import com.buildingcompany.services.IConnectionPool;

public class CompanyDAOImpl extends DAOImpl implements CompanyDAO {
    private static Logger logger = LogManager.getLogger(CompanyDAOImpl.class);
    private AddressDAO addressDAO;
    private final String GET_COMPANIES_ADDRESSES_BY_COUNTRY_INDUSTRY = "SELECT address_id, id, name, industry.name\n"
    + "FROM company JOIN industry ON company.industry_id = industry.id\n"
        + "JOIN address ON company.address_id = address.id\n"
        + "JOIN country ON address.country_id = country.id\n"
    + "WHERE country.name = ? AND industry.name = ?;";

    public CompanyDAOImpl() {
        super();
    }

    public CompanyDAOImpl(IConnectionPool connectionPool) {
        super(connectionPool);
    }
    
    public CompanyDAOImpl(IConnectionPool connectionPool, AddressDAO addressDAO) {
        this.connectionPool = connectionPool;
        this.addressDAO = addressDAO;
    }

    public List<Company> getCompaniesByCountryIndustry(String countryName, String industry) {
        List<Company> companies = new ArrayList<>();
        String query = GET_COMPANIES_ADDRESSES_BY_COUNTRY_INDUSTRY;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            ResultSet rs = null;
            try(PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, countryName);
                statement.setString(2, industry);
                rs = statement.executeQuery();
                while(rs.next()) {
                    int addressId = rs.getInt("address_id");
                    // TODO(khncao): more atomic? split functionality to other method?
                    Address address = addressDAO.getAddressById(addressId);
                    // TODO(khncao): check if address id cached, else query
                    Company company = new Company(
                        rs.getInt("id"), 
                        rs.getString("name"), 
                        address, 
                        rs.getString("industry.name"));
                    companies.add(company);
                }
            } finally {
                if(rs != null) rs.close();
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            connectionPool.freeConnection(conn);
        }
        return companies;
    }
}
