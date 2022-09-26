package com.buildingcompany.services;

import java.sql.Connection;

public interface IConnectionPool {
    Connection getConnection();
    void freeConnection(Connection conn);
}
