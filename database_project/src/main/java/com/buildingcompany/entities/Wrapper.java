package com.buildingcompany.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAnyElement;

public class Wrapper<T> {
    private List<T> items = new ArrayList<>();

    @XmlAnyElement(lax = true)
    public List<T> getItems() {
        return items;
    }
}
