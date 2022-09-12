module com.buildingcompany {
    requires transitive javafx.controls;
    requires transitive java.sql;

    exports com.buildingcompany;
    exports com.buildingcompany.dao;
    exports com.buildingcompany.entities;
    exports com.buildingcompany.controllers;
    exports com.buildingcompany.views;
    exports com.buildingcompany.services;
    exports com.buildingcompany.exceptions;
}
