package com.buildingcompany.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface ResourcePaths {
    static final Logger logger = LogManager.getLogger(ResourcePaths.class);
    public static final String basePath = "src/main/resources/";
    public static final String xmlPath = basePath + "xml/";
    public static final String xsdPath = basePath + "xsd/";
    public static final String propsPath = basePath + "properties/";
    
    public static final String dbPropUrl = propsPath + "db.properties";
    public static final String myBatisConfigFileName = "mybatis-config.xml";
    public static final String mybatisConfigUrl = basePath + myBatisConfigFileName;

    static Properties dbProperties = new Properties();

    static Properties getDatabaseProperties() {
        if(!dbProperties.isEmpty()) {
            return dbProperties;
        }
        try {
            dbProperties.load(getResourceAsStream(dbPropUrl));
        } catch(IOException e) {
            logger.error(e);
        }
        return dbProperties;
    }

    static InputStream getResourceAsStream(String path) {
        try {
            File file = new File(path);
            return new FileInputStream(file);
        } catch(FileNotFoundException e) {
            logger.error(e);
        }
        return null;
    }
}
