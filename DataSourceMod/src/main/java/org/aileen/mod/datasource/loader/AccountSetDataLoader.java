package org.aileen.mod.datasource.loader;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.databind.NacosDataSourceSet;
import org.aileen.mod.datasource.model.AccountSet;
import org.aileen.mod.datasource.model.DataSourceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private DataSourceSetLocal dataSourceSetLocal;

    private Map<String, Map<String, DataSourceData>> allDataSourceData = new HashMap<>();

    @PostConstruct
    public void init(){
        List<AccountSet> accountSets = getAccountSets();
        try {
            log.debug(objectMapper.writeValueAsString(accountSets));
        }catch (Exception e){
            log.error("Failed to load configuration ", e);
        }
    }

    private void initDataSourceData4Local() {
        try {
            log.info("Loading configuration from local file: {}", filePath);
            // 从类路径下的资源文件加载配置
            Resource resource = resourceLoader.getResource("classpath:" + filePath);
            dataSourceSetLocal = objectMapper.readValue(resource.getInputStream(), new TypeReference<DataSourceSetLocal>() {});
        } catch (IOException e) {
            log.error("Failed to load configuration from local file", e);
            throw new RuntimeException("Failed to load configuration from local file", e);
        }
    }

    /** 获取账套配置 */
    public List<AccountSet> getAccountSets() {
        if(nacosEnable){
            log.debug("Get From Nacos!");
            return nacosDataSourceSet.getAccountSets();
        }else{
            log.debug("Get From Local!");
            if(dataSourceSetLocal == null){
                initDataSourceData4Local();
            }
            return dataSourceSetLocal.getDataSourceSet().getAccountSets();
        }
    }

    public String getDefaultAccountSetName(){
        for(AccountSet accountSet : getAccountSets()){
            if(accountSet.getIsDefault()){
                return accountSet.getAccountSetName();
            }
        }
        return null;
    }

    public Map<String, DataSourceData> getDataSourceDataMap(String dbId){
        if(allDataSourceData.containsKey(dbId)){
            return allDataSourceData.get(dbId);
        }
        Map<String, DataSourceData> dataSourceDataMap = new HashMap<>();
        for(AccountSet accountSet : getAccountSets()){
            for (DataSourceData data : accountSet.getData()){
                if(data.getDBId().equals(dbId)){
                    dataSourceDataMap.put(accountSet.getAccountSetName(), data);
                    break;
                }
            }
        }
        allDataSourceData.put(dbId, dataSourceDataMap);
        return dataSourceDataMap;
    }
}

@Data
class DataSourceSetLocal {
    @JsonProperty("datasource-set")
    private DataSourceSet dataSourceSet;
}

@Data
class DataSourceSet {
    private List<AccountSet> accountSets;
}



