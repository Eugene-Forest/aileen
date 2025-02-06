package org.aileen.lc.dao;

import org.aileen.lc.model.TSEntityFieldReference;

public interface TSEntityFieldReferenceMapper {
    int deleteByPrimaryKey(String ID);

    int insert(TSEntityFieldReference record);

    int insertSelective(TSEntityFieldReference record);

    TSEntityFieldReference selectByPrimaryKey(String ID);

    int updateByPrimaryKeySelective(TSEntityFieldReference record);

    int updateByPrimaryKeyWithBLOBs(TSEntityFieldReference record);
}