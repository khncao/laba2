package com.buildingcompany.dao.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;

import com.buildingcompany.services.mybatis.MyBatis;

public abstract class DAOImpl {
    protected SqlSessionFactory sqlSessionFactory;

    protected DAOImpl() {
        sqlSessionFactory = MyBatis.getSqlSessionFactory();
    }

    protected DAOImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
}
