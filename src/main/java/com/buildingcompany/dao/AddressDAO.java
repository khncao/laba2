package com.buildingcompany.dao;

import java.util.List;

import com.buildingcompany.entities.Address;

public interface AddressDAO extends DAO<Address> {
    Address getAddressById(int primaryKey);
    List<Address> getAddressesByCity(String cityName, String countryName);
    List<Address> getAddressesByCountry(String countryName);
}
