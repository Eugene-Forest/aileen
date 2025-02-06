package org.aileen.lc.dao;

import org.aileen.lc.model.TSDataModel;

public interface TSDataModelMapper {
    int deleteByPrimaryKey(String modelID);

    int insert(TSDataModel record);

    int insertSelective(TSDataModel record);

    TSDataModel selectByPrimaryKey(String modelID);

    int updateByPrimaryKeySelective(TSDataModel record);

    int updateByPrimaryKey(TSDataModel record);
}