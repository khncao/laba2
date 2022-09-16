module com.buildingcompany {
    requires transitive javafx.controls;
    requires transitive java.sql;
    requires org.apache.logging.log4j;
    requires jakarta.xml.bind;

    exports com.buildingcompany;
    exports com.buildingcompany.dao;
    exports com.buildingcompany.entities;
    exports com.buildingcompany.controllers;
    exports com.buildingcompany.views;
    exports com.buildingcompany.services;
    exports com.buildingcompany.utility.exceptions;

    opens com.buildingcompany.entities to jakarta.xml.bind;
}
