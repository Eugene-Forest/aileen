package org.aileen.mod.datasource.loader;

import org.aileen.mod.datasource.model.AccountSet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 绑定Nacos配置信息的对象
 */
@Component
@RefreshScope
@ConfigurationProperties(prefix = "datasource-set")
public class NacosDataSourceSet {

    private List<AccountSet> accountSets;

    public List<AccountSet> getAccountSets() {
        return accountSets;
    }

    public void setAccountSets(List<AccountSet> accountSets) {
        this.accountSets = accountSets;
    }
}
