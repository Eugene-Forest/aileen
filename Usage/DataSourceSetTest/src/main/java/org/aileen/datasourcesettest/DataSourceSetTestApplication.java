package org.aileen.datasourcesettest;

import org.aileen.mod.datasource.databind.NacosDataSourceSet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@EnableConfigurationProperties(NacosDataSourceSet.class)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class}, scanBasePackages = {"org.aileen"})
public class DataSourceSetTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(org.aileen.mod.datasource.DataSourceApp.class, args);
    }
}
