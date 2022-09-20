package com.buildingcompany.dao;

import java.util.List;

import com.buildingcompany.entities.Company;

public interface CompanyDAO {
    List<Company> getCompaniesByCountryIndustry(String countryName, String industry);
}
