package com.buildingcompany.services.mybatis;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.buildingcompany.utility.ResourcePaths;

public class MyBatis {
    private final static Logger logger = LogManager.getLogger(MyBatis.class);
    private static SqlSessionFactory factory;

    private MyBatis() {
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        if(factory == null) {
            initialize();
        }
        if(factory == null) {
            logger.error("SqlSessionFactory failed to initialize");
        }
        return factory;
    }

    private static void initialize() {
        try {
            InputStream input = Resources.getResourceAsStream(ResourcePaths.myBatisConfigFileName);
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            factory = builder.build(input);
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
