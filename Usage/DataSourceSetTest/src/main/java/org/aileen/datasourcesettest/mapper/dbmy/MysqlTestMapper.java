package org.aileen.datasourcesettest.mapper.dbmy;

import org.aileen.datasourcesettest.model.MysqlTest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MysqlTestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MysqlTest record);

    int insertSelective(MysqlTest record);

    MysqlTest selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MysqlTest record);

    int updateByPrimaryKey(MysqlTest record);
}