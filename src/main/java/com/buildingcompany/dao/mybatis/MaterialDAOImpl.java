package com.buildingcompany.dao.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.buildingcompany.dao.MaterialDAO;
import com.buildingcompany.dao.mybatis.mappers.MaterialMapper;
import com.buildingcompany.entities.Material;

public class MaterialDAOImpl extends DAOImpl implements MaterialDAO {
    public MaterialDAOImpl() {
        super();
    }

    public MaterialDAOImpl(SqlSessionFactory sessionFactory) {
        this.sqlSessionFactory = sessionFactory;
    }

    public Material getMaterialById(int id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.getMapper(MaterialMapper.class).selectMaterial(id);
        }
    }

    public Material getMaterialCountryAvgCost(Material material) {
        throw new UnsupportedOperationException();
    }
}
