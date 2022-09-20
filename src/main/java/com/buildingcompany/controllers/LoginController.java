package com.buildingcompany.controllers;

import com.buildingcompany.services.MySqlConnection;

public class LoginController {
    public static boolean tryLogin(String port, String dbName, String username, String password) {
        return MySqlConnection.getInstance().initialize(port, dbName, username, password, MySqlConnection.DefaultPoolSize);
    }
}
