package org.aileen.mod.datasource.nacos;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.NacosFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aileen.mod.datasource.model.AccountSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;

@Slf4j
@Component
@RefreshScope
public class NacosConfigFetcher {

    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;

    @Value("${spring.cloud.nacos.config.namespace}")
    private String namespace;

    @Value("${datasourcemod.nacos-enable:false}") // 默认为false
    private boolean nacosEnable;

    private ConfigService configService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<AccountSet> accountSets;

    @PostConstruct
    public void init() {
        if (nacosEnable) {
            try {
                Properties properties = new Properties();
                properties.put("serverAddr", serverAddr);
                properties.put("namespace", namespace);
                configService = NacosFactory.createConfigService(properties);
                accountSets = getAccountSets();
            } catch (Exception e) {
                 log.error("Failed to initialize NacosConfigFetcher", e);
            }
        } else {
             log.info("Nacos configuration is disabled.");
        }
    }

    private <T> T getConfig(String dataId, String group, TypeReference<T> typeReference) throws Exception {
        if (!nacosEnable) {
            throw new IllegalStateException("Nacos configuration is disabled.");
        }
        String content = configService.getConfig(dataId, group, 5000);
        return objectMapper.readValue(content, typeReference);
    }

    private List<AccountSet> getAccountSets() throws Exception {
        if (!nacosEnable) {
            throw new IllegalStateException("Nacos configuration is disabled.");
        }
        return getConfig("datasourceSet", "DEFAULT_GROUP", new TypeReference<List<AccountSet>>() {});
    }

    public List<AccountSet> getAccountSetsFromNacos() {
        if (!nacosEnable) {
            throw new IllegalStateException("Nacos configuration is disabled.");
        }
        return accountSets;
    }
}
