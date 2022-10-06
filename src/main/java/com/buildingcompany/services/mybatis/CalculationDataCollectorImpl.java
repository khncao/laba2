package com.buildingcompany.services.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.buildingcompany.entities.BuildingType;
import com.buildingcompany.mappers.BuildingTypeMapper;
import com.buildingcompany.services.ICalculationDataCollector;

public class CalculationDataCollectorImpl implements ICalculationDataCollector {
    private SqlSessionFactory sqlSessionFactory;

    public CalculationDataCollectorImpl() {
        sqlSessionFactory = MyBatis.getSqlSessionFactory();
    }
    
    public BuildingType getBuildingType(String buildingTypeName) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.getMapper(BuildingTypeMapper.class).selectBuildingTypeByName(buildingTypeName);
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
    // TODO(khncao): methods for lazy loading but mybatis mapper implementation currently loads everything at once
    public BuildingType getMaterialRequirements(BuildingType buildingType) {
        return buildingType;
    }

    public BuildingType getToolRequirements(BuildingType buildingType) {
        return buildingType;
    }
}
