package com.buildingcompany.services.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class MySqlConnectionTest {
    private final static Logger logger = LogManager.getLogger(MySqlConnectionTest.class);
    // TODO(khncao): currently works by manually freeing connections. Should probably handle singleton better; avoid/refactor, use interfaces, or reset singleton state
    private MySqlConnection mySqlConnection = MySqlConnection.getInstance();
    private List<Connection> connectionsCache = new ArrayList<>();

    @AfterMethod
    void tearDown() {
        // TODO(khncao): relies on manually adding popped connections to track and
        // cleanup; popped connections could be contained in MySqlConnection for easy
        // cleanup
        connectionsCache.forEach(x -> mySqlConnection.freeConnection(x));
        connectionsCache.clear();
    }

    @Test
    void testGetInstance() {
        MySqlConnection mySqlConnection2 = MySqlConnection.getInstance();
        assertEquals(mySqlConnection2, mySqlConnection, "Singleton getInstance did not return same instance");
    }

    @Test
    void testGetConnectionValidAndNotNull() {
        Connection connection = mySqlConnection.getConnection();
        assertNotNull(connection, "getConnection should not return null connection if fresh pool");
        connectionsCache.add(connection);
        try {
            assertTrue(connection.isValid(0), "Connection should be valid");
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Test
    void testGetConnectionNullIfOverdrawn() {
        getConnectionsToCache(MySqlConnection.DefaultPoolSize);
        Connection overdrawnConn = mySqlConnection.getConnection();
        assertNull(overdrawnConn, "getConnection succeeded despite expected null result from maximum pool size");
    }

    @Test
    void testIsConnectionValid() {
        Connection conn1 = getConnectionToCache();
        assertTrue(MySqlConnection.isConnectionValid(conn1), "Single connection from fresh pool should give valid connection");
    }

    @Test
    void testIsConnectionValidFailIfClosed() {
        Connection conn2 = getConnectionToCache();
        try {
            conn2.close();
        } catch(SQLException e) {
            logger.error(e);
        }
        assertFalse(MySqlConnection.isConnectionValid(conn2), "Connection valid despite expectations after calling close()");
    }

    @Test
    void testFreeConnection() {
        getConnectionsToCache(MySqlConnection.DefaultPoolSize);
        Connection overdrawnConn = mySqlConnection.getConnection();
        assertNull(overdrawnConn, "Non-null connection despite expected null due to pool size");
        connectionsCache.forEach((x) -> mySqlConnection.freeConnection(x));
        getConnectionsToCache(MySqlConnection.DefaultPoolSize);
    }

    private Connection getConnectionToCache() {
        Connection conn = mySqlConnection.getConnection();
        assertNotNull(conn, "Connection should not be null");
        connectionsCache.add(conn);
        return conn;
    }

    private void getConnectionsToCache(int amount) {
        for (int i = 0; i < amount; ++i) {
            Connection conn = mySqlConnection.getConnection();
            connectionsCache.add(conn);
            assertNotNull(conn, "Connection should not be null");
        }
    }
}
