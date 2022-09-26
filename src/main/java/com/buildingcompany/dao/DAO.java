package com.buildingcompany.dao;

import java.util.List;

public interface DAO<T> {
    void create(T t);
    List<T> readAll();
    void update(T t);
    void delete(T t);

    /**
     * For relational fields that are stored in entities with their String names instead of id. Maybe store id by default and get/cache name as needed instead
     * @param table table where id and name are stored
     * @param name name to look up
     * @return
     */
    public static String generateGetIdFromName(String table, String name) {
        return String.format("(SELECT %s.id FROM %s WHERE %s.name = %s)", table, table, table, name);
    }
}
