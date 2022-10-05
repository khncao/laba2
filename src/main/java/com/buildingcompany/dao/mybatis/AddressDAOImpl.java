package com.buildingcompany.dao.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.buildingcompany.dao.AddressDAO;
import com.buildingcompany.entities.Address;
import com.buildingcompany.mappers.AddressMapper;

public class AddressDAOImpl extends DAOImpl implements AddressDAO {
    public AddressDAOImpl() {
        super();
    }

    public AddressDAOImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    public Address getAddressById(int id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.getMapper(AddressMapper.class).selectAddress(id);
        }
    }

    public List<Address> getAddressesByCity(String cityName, String countryName) {
        throw new UnsupportedOperationException();
    }

    public List<Address> getAddressesByCountry(String countryName) {
        throw new UnsupportedOperationException();
    }

    public void create(Address t) {
        throw new UnsupportedOperationException();
    }

    public List<Address> readAll() {
        throw new UnsupportedOperationException();
    }

    public void update(Address t) {
        throw new UnsupportedOperationException();
    }

    public void delete(Address t) {
        throw new UnsupportedOperationException();
    }
}
