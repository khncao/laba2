package com.buildingcompany.dao.sax;

import java.util.Map;

import com.buildingcompany.entities.Address;

public class AddressHandler extends GenericCollectionHandler<Address> {
    public AddressHandler() {
        super(Address.class);
    }

    @Override
    public void processElement(Map<String, String> map) {
        Address address = new Address(
            Integer.parseInt(map.get("id")), 
            map.getOrDefault("line1", ""), 
            map.getOrDefault("line2", ""), 
            map.getOrDefault("line3", ""), 
            map.get("country"), 
            map.get("city"), 
            map.getOrDefault("zipcode", "")
        );
        results.add(address);
    }
}
