package org.aileen.lc.dao;

import org.aileen.lc.model.TSEntityField;

public interface TSEntityFieldMapper {
    int deleteByPrimaryKey(String ID);

    int insert(TSEntityField record);

    int insertSelective(TSEntityField record);

    TSEntityField selectByPrimaryKey(String ID);

    int updateByPrimaryKeySelective(TSEntityField record);

    int updateByPrimaryKey(TSEntityField record);
}