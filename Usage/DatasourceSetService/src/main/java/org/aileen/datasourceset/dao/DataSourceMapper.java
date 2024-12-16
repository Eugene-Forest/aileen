package org.aileen.datasourceset.dao;

import org.aileen.datasourceset.dto.DataSourceDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


import java.util.List;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/23
 */
@Mapper
public interface DataSourceMapper {

    @Select("select * from TCDataSource")
    List<DataSourceDto> selectAllDataSources();
}
