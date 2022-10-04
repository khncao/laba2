module com.buildingcompany {
    requires transitive javafx.controls;
    requires transitive java.sql;
    requires transitive java.desktop;
    requires org.apache.logging.log4j;
    requires jakarta.xml.bind;
    requires com.fasterxml.jackson.databind;
    requires org.mybatis;

    exports com.buildingcompany;
    exports com.buildingcompany.dao;
    exports com.buildingcompany.entities;
    exports com.buildingcompany.controllers;
    exports com.buildingcompany.views;
    exports com.buildingcompany.services;
    exports com.buildingcompany.utility;
    exports com.buildingcompany.utility.exceptions;
    exports com.buildingcompany.utility.adapters;

    opens com.buildingcompany.entities to jakarta.xml.bind, com.fasterxml.jackson.databind;
    opens com.buildingcompany.dao.mybatis.mappers;
    opens properties;
}
