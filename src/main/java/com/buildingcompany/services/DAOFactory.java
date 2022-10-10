package com.buildingcompany.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DAOFactory {
    private final static Logger logger = LogManager.getLogger(DAOFactory.class);
    private final static String baseDaoPath = "com.buildingcompany.dao.";
    private final static String implSuffix = "DAOImpl";
    private static String defaultImpl = null;

    // TODO(khncao): T extends DAO. Maybe Class<T> for casting or suppress warnings
    /**
     * @param <T> returned DAOImpl instance type
     * @param entityClass class of data entity/POJO; used as part of path
     * @param implName jdbc, mybatis, 
     * @return instance of DAOImpl from implName package
     */
    public static <T> T getDaoImpl(Class entityClass, String implName) {
        String pkg = "";
        switch(implName.toLowerCase()) {
            case("jdbc"): {
                pkg = "jdbc.";
                break;
            }
            case("mybatis"): {
                pkg = "mybatis.";
                break;
            }
            default: {
                logger.error("Implementation not supported: " + implName);
                return null;
            }
        }
        T instance = null;
        try {
            String implPath = baseDaoPath + pkg + entityClass.getSimpleName() + implSuffix;
            logger.info("DAOFactory parse class path: " + implPath);
            instance = (T)Class.forName(implPath).getDeclaredConstructor().newInstance();
        } catch(ClassNotFoundException e) {
            logger.error(e);
        } catch(InstantiationException e) {
            logger.error(e);
        } catch(NoSuchMethodException e) {
            logger.error(e);
        } catch(IllegalAccessException e) {
            logger.error(e);
        } catch(Exception e) {
            logger.error(e + ". General exception caught");
        }
        return instance;
    }

    public static <T> T getDaoImpl(Class entityClass) {
        if(defaultImpl == null) {
            logger.error("No default implementation assigned");
            return null;
        }
        return getDaoImpl(entityClass, defaultImpl);
    }

    public static String getDefaultImpl() {
        return defaultImpl;
    }

    public static void setDefaultImpl(String defaultImpl) {
        DAOFactory.defaultImpl = defaultImpl;
    }
}
