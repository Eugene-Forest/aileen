package org.aileen.mod.datasource.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.model.AccountSet;
import org.aileen.mod.datasource.model.DataSourceSet;
import org.aileen.mod.datasource.nacos.NacosDataSourceSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * 账套资源载入
 */
@Slf4j
@Component
public class AccountSetDataLoader {

    @Value("${datasource-mod.nacos-enable:false}")
    private boolean nacosEnable;

    @Value("${datasource-mod.file-path}")
    private String filePath;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NacosDataSourceSet nacosDataSourceSet;

    private DataSourceSet dataSourceSet;
    private void initDataSourceData4Local() {
        try {
            log.info("Loading configuration from local file: {}", filePath);
            // 从类路径下的资源文件加载配置
            Resource resource = resourceLoader.getResource("classpath:" + filePath);
            dataSourceSet = objectMapper.readValue(resource.getInputStream(), new TypeReference<DataSourceSet>() {});
        } catch (IOException e) {
            log.error("Failed to load configuration from local file", e);
            throw new RuntimeException("Failed to load configuration from local file", e);
        }
    }

    /** 获取账套配置 */
    public List<AccountSet> getAccountSets() {
        if(nacosEnable){
            log.debug("Get From Nacos!");
            log.debug(nacosDataSourceSet.getUrl());
            return nacosDataSourceSet.getAccountSets();
        }else{
            log.debug("Get From Local!");
            if(dataSourceSet == null){
                initDataSourceData4Local();
            }
            return dataSourceSet.getAccountSets();
        }
    }


}
