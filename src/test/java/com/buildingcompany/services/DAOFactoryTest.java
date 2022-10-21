package com.buildingcompany.services;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.*;

import com.buildingcompany.entities.Address;
import com.buildingcompany.entities.Tool;

public class DAOFactoryTest {
    // TODO(khncao): would be useful to check that every iteration of entity & implementation input returns correct daoImpl; maybe enumerated entity classes and implementations somewhere
    @Test
    void testGetDaoImplWithValidInputs() {
        SoftAssert softAssert = new SoftAssert();
        Object daoImpl1 = DAOFactory.getDaoImpl(Address.class, "jdbc");
        softAssert.assertNotNull(daoImpl1, "getDaoImpl for Address & jdbc failed; should not be null");
        softAssert.assertEquals(com.buildingcompany.dao.jdbc.AddressDAOImpl.class, daoImpl1.getClass(), "Input of Address & jdbc did not return correct result dao implementation");
        
        Object daoImpl2 = DAOFactory.getDaoImpl(Address.class, "mybatis");
        softAssert.assertNotNull(daoImpl2, "getDaoImpl for Address & mybatis failed; should not be null");
        softAssert.assertEquals(com.buildingcompany.dao.mybatis.AddressDAOImpl.class, daoImpl2.getClass(), "Input of Address & mybatis did not return correct result dao implementation");

        Object daoImpl3 = DAOFactory.getDaoImpl(Tool.class, "jdbc");
        softAssert.assertNotNull(daoImpl3, "getDaoImpl for Tool & jdbc failed; should not be null");
        softAssert.assertEquals(com.buildingcompany.dao.jdbc.ToolDAOImpl.class, daoImpl3.getClass(), "Input of Tool & jdbc did not return correct result dao implementation");
        softAssert.assertAll();
    }

    @Test
    void testGetDaoImplWithInvalidInputs() {
        Object daoImpl1 = DAOFactory.getDaoImpl(Address.class, "x");
        assertNull(daoImpl1, "Returned object when not expected after invalid implementation input");
        Object daoImpl2 = DAOFactory.getDaoImpl(DAOFactory.class, "jdbc");
        assertNull(daoImpl2, "Returned object when not expected after invalid entity class input");
    }

    @Test
    void testSetDefaultImpl() {
        String defaultImpl = DAOFactory.getDefaultImpl();
        DAOFactory.setDefaultImpl("test1");
        assertNotEquals(defaultImpl, "test1", "defaultImpl variable should not equal cached value after setting");
        DAOFactory.setDefaultImpl(defaultImpl);
    }

    @Test(dependsOnMethods = "testSetDefaultImpl")
    void testGetDaoImplWithDefaultImpl() {
        Object daoImpl1 = DAOFactory.getDaoImpl(Address.class);
        assertNull(daoImpl1, "Returned object when not expected as defaultImpl should be null");
        
        DAOFactory.setDefaultImpl("jdbc");
        daoImpl1 = DAOFactory.getDaoImpl(Address.class);
        assertNotNull(daoImpl1, "Did not return dao impl with defaultImpl as expected");
        assertEquals(com.buildingcompany.dao.jdbc.AddressDAOImpl.class, daoImpl1.getClass(), "Returned dao impl does not match expected defaultImpl");
    }
}
