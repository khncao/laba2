package com.buildingcompany.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MySqlConnection implements IConnectionPool {
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
            System.out.println("Connection pool not initialized");
            return null;
        }
        Connection pop = null;
        try {
            boolean hasValidConnection = false;
            do {
                pop = connections.take();
                if(!isConnectionValid(pop)) {
                    System.out.println("Invalid connection dequeued. Removing from pool");
                } else {
                    hasValidConnection = true;
                }
            } while(!hasValidConnection);
        } catch(InterruptedException e) {
            System.out.println(e.toString());
        }
        return pop;
    }

    public synchronized void freeConnection(Connection conn) {
        try {
            if(!isConnectionValid(conn)) {
                System.out.println("Invalid connection freed. Removing from pool");
                return;
            }
            if(connections.contains(conn)) {
                System.out.println("Error: attempted to insert same connection back into pool");
                return;
            }
            connections.put(conn);
        } catch(InterruptedException e) {
            System.out.println(e.toString());
        }
    }

    public boolean initialize(String port, String dbName, String user, String password, int poolSize) {
        if(connections != null && connections.size() > 0) {
            System.out.println("Connection pool already initialized");
            return true;
        }
        connections = new ArrayBlockingQueue<>(poolSize, true);
        try {
            for(int i = 0; i < poolSize; ++i) {
                Connection conn = DriverManager.getConnection(url + port + "/" + dbName, user, password);
                if(!isConnectionValid(conn)) {
                    System.out.println("Error: invalid connection during connection pool init");
                    return false;
                }
                if(!connections.offer(conn)) {
                    System.out.println("Error: failed to add connection to pool");
                    return false;
                }
            }
        } catch(SQLTimeoutException e) {
            System.out.println(e.toString());
        } catch(SQLException e) {
            System.out.println(e.toString());
        }
        System.out.println("Initialized connection pool with " + connections.size() + " connections");
        return connections.size() > 0;
    }

    public boolean isConnectionValid(Connection conn) {
        try {
            if(conn == null || conn.isClosed() || !conn.isValid(0)) {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return true;
    }
}
