package com.buildingcompany.dao.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.buildingcompany.dao.BuildingTypeDAO;
import com.buildingcompany.entities.BuildingType;
import com.buildingcompany.mappers.BuildingTypeMapper;

public class BuildingTypeDAOImpl extends DAOImpl implements BuildingTypeDAO {
    public BuildingTypeDAOImpl() {
        super();
    }

    public BuildingTypeDAOImpl(SqlSessionFactory sessionFactory) {
        this.sqlSessionFactory = sessionFactory;
    }

    public BuildingType selectBuildingType(int id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.getMapper(BuildingTypeMapper.class).selectBuildingType(id);
        }
    }

    public BuildingType getBuildingType(String bldgTypeName) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.getMapper(BuildingTypeMapper.class).selectBuildingTypeByName(bldgTypeName);
        }
    }

    public int getBuildingTypeChoiceNames(List<String> buildingTypeNames) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            List<String> choices = session.getMapper(BuildingTypeMapper.class).selectBuildingTypeNames();
            buildingTypeNames.clear();
            buildingTypeNames.addAll(choices);
            return buildingTypeNames.size();
        }
    }

    // TODO(khncao): lazy load
    public BuildingType getMaterialRequirements(BuildingType buildingType) {
        return buildingType;
    }

    public BuildingType getToolRequirements(BuildingType buildingType) {
        return buildingType;
    }

    public BuildingType getLaborRequirements(BuildingType buildingType) {
        throw new UnsupportedOperationException("TODO");
    }
}
