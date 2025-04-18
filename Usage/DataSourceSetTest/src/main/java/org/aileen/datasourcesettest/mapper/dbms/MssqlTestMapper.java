package org.aileen.datasourcesettest.mapper.dbms;

import org.aileen.datasourcesettest.model.MssqlTest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MssqlTestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MssqlTest record);

    int insertSelective(MssqlTest record);

    MssqlTest selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MssqlTest record);

    int updateByPrimaryKey(MssqlTest record);

    List<MssqlTest> getMssqlTestList();
}