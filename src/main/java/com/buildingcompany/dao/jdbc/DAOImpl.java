package com.buildingcompany.dao.jdbc;

import com.buildingcompany.services.IConnectionPool;
import com.buildingcompany.services.jdbc.MySqlConnection;

public abstract class DAOImpl {
    protected IConnectionPool connectionPool;

    protected DAOImpl() {
        connectionPool = MySqlConnection.getInstance();
    }

    protected DAOImpl(IConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }
}
