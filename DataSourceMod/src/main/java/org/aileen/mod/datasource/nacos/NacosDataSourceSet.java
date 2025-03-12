package org.aileen.mod.datasource.nacos;

import org.aileen.mod.datasource.model.AccountSet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RefreshScope
@ConfigurationProperties(prefix = "datasource-set")
public class NacosDataSourceSet {

    private String url;

    private List<AccountSet> accountSets;

    public NacosDataSourceSet() {
    }

    public NacosDataSourceSet(List<AccountSet> accountSets) {
        this.accountSets = accountSets;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<AccountSet> getAccountSets() {
        return accountSets;
    }

    public void setAccountSets(List<AccountSet> accountSets) {
        this.accountSets = accountSets;
    }
}
