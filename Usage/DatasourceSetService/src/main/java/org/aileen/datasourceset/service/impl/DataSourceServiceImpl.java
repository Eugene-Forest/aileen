package org.aileen.datasourceset.service.impl;

import org.aileen.datasourceset.dao.DataSourceMapper;
import org.aileen.datasourceset.dto.DataSourceDto;
import org.aileen.datasourceset.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/23
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Override
    public List<DataSourceDto> getDataSources() {
        List<DataSourceDto> res = dataSourceMapper.selectAllDataSources();
        return res;
    }
}
