package com.buildingcompany.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MySqlConnection implements IConnectionPool {
    private static Logger logger = LogManager.getLogger(MySqlConnection.class);
    public final static int DefaultPoolSize = 10;
    private final static String url = "jdbc:mysql://localhost:";
    private static MySqlConnection instance;

    private BlockingQueue<Connection> connections;

    private MySqlConnection() {
        
    }

    public static MySqlConnection getInstance() {
        if(instance == null) {
            instance = new MySqlConnection();
        }
        return instance;
    }

    public synchronized Connection getConnection() {
        if(connections == null || connections.size() < 1) {
            logger.error("Connection pool not initialized");
            return null;
        }
        Connection pop = null;
        try {
            boolean hasValidConnection = false;
            do {
                pop = connections.take();
                if(!isConnectionValid(pop)) {
                    logger.warn("Invalid connection dequeued. Removing from pool");
                } else {
                    hasValidConnection = true;
                }
            } while(!hasValidConnection);
        } catch(InterruptedException e) {
            logger.error(e.toString());
        }
        return pop;
    }

    public synchronized void freeConnection(Connection conn) {
        try {
            if(!isConnectionValid(conn)) {
                logger.warn("Invalid connection freed. Removing from pool");
                return;
            }
            if(connections.contains(conn)) {
                logger.error("Error: attempted to insert same connection back into pool");
                return;
            }
            connections.put(conn);
        } catch(InterruptedException e) {
            logger.error(e.toString());
        }
    }

    public boolean initialize(String port, String dbName, String user, String password, int poolSize) {
        if(connections != null && connections.size() > 0) {
            logger.warn("Connection pool already initialized");
            return true;
        }
        connections = new ArrayBlockingQueue<>(poolSize, true);
        try {
            for(int i = 0; i < poolSize; ++i) {
                Connection conn = DriverManager.getConnection(url + port + "/" + dbName, user, password);
                if(!isConnectionValid(conn)) {
                    logger.error("Error: invalid connection during connection pool init");
                    return false;
                }
                if(!connections.offer(conn)) {
                    logger.error("Error: failed to add connection to pool");
                    return false;
                }
            }
        } catch(SQLTimeoutException e) {
            logger.error(e.toString());
        } catch(SQLException e) {
            logger.error(e.toString());
        }
        logger.info("Initialized connection pool with " + connections.size() + " connections");
        return connections.size() > 0;
    }

    public boolean isConnectionValid(Connection conn) {
        try {
            if(conn == null || conn.isClosed() || !conn.isValid(0)) {
                return false;
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        return true;
    }
}
