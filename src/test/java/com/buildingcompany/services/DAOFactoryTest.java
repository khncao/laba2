package com.buildingcompany.services;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

import com.buildingcompany.entities.Address;
import com.buildingcompany.entities.Tool;

public class DAOFactoryTest {
    // TODO(khncao): would be useful to check that every iteration of entity & implementation input returns correct daoImpl; maybe enumerated entity classes and implementations somewhere
    @Test
    void testGetDaoImplWithValidInputs() {
        Object daoImpl1 = DAOFactory.getDaoImpl(Address.class, "jdbc");
        assertNotNull(daoImpl1);
        assertEquals(com.buildingcompany.dao.jdbc.AddressDAOImpl.class, daoImpl1.getClass());
        
        Object daoImpl2 = DAOFactory.getDaoImpl(Address.class, "mybatis");
        assertNotNull(daoImpl2);
        assertEquals(com.buildingcompany.dao.mybatis.AddressDAOImpl.class, daoImpl2.getClass());

        Object daoImpl3 = DAOFactory.getDaoImpl(Tool.class, "jdbc");
        assertNotNull(daoImpl3);
        assertEquals(com.buildingcompany.dao.jdbc.ToolDAOImpl.class, daoImpl3.getClass());
    }

    @Test
    void testGetDaoImplWithInvalidInputs() {
        Object daoImpl1 = DAOFactory.getDaoImpl(Address.class, "x");
        assertNull(daoImpl1);
        Object daoImpl2 = DAOFactory.getDaoImpl(DAOFactory.class, "jdbc");
        assertNull(daoImpl2);
    }

    @Test
    void testSetDefaultImpl() {
        String defaultImpl = DAOFactory.getDefaultImpl();
        DAOFactory.setDefaultImpl("test1");
        assertNotEquals(defaultImpl, "test1");
        DAOFactory.setDefaultImpl(defaultImpl);
    }

    @Test(dependsOnMethods = "testSetDefaultImpl")
    void testGetDaoImplWithDefaultImpl() {
        Object daoImpl1 = DAOFactory.getDaoImpl(Address.class);
        assertNull(daoImpl1);
        
        DAOFactory.setDefaultImpl("jdbc");
        daoImpl1 = DAOFactory.getDaoImpl(Address.class);
        assertNotNull(daoImpl1);
        assertEquals(com.buildingcompany.dao.jdbc.AddressDAOImpl.class, daoImpl1.getClass());
    }
}
