package com.buildingcompany.dao.mybatis.mappers;

import com.buildingcompany.entities.Address;

public interface AddressMapper {
    Address selectAddress(int id);
}
