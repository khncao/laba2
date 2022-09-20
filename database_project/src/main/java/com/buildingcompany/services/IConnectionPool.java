package com.buildingcompany.services;

import java.sql.Connection;

public interface IConnectionPool {
    Connection getConnection();
    void freeConnection(Connection conn);
    boolean initialize(String port, String dbName, String user, String password, int poolSize);
}
