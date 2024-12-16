package org.aileen.datasourceset.service;



import org.aileen.datasourceset.dto.DataSourceDto;

import java.util.List;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/23
 */
public interface DataSourceService {
    List<DataSourceDto> getDataSources();
}
