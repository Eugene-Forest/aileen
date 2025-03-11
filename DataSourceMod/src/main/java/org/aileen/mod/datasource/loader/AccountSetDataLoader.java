package org.aileen.mod.datasource.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.model.AccountSet;
import org.aileen.mod.datasource.nacos.NacosConfigFetcher;
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

    @Value("${datasourcemod.nacos-enable:false}")
    private boolean nacosEnable;

    @Value("${datasourcemod.file-path}")
    private String filePath;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NacosConfigFetcher nacosConfigFetcher;

    private List<AccountSet> accountSets;
    private void initDataSourceData4Local() {
        try {
            log.info("Loading configuration from local file: {}", filePath);
            // 从类路径下的资源文件加载配置
            Resource resource = resourceLoader.getResource("classpath:" + filePath);
            accountSets = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<AccountSet>>() {
            });
        } catch (IOException e) {
            log.error("Failed to load configuration from local file", e);
            throw new RuntimeException("Failed to load configuration from local file", e);
        }
    }

    /** 获取账套配置 */
    public List<AccountSet> getAccountSets() {
        if(nacosEnable){
            return nacosConfigFetcher.getAccountSetsFromNacos();
        }else{
            if(accountSets == null){
                initDataSourceData4Local();
            }
            return accountSets;
        }
    }


}
