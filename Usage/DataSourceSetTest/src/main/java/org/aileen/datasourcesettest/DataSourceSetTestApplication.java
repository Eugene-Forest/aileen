package org.aileen.datasourcesettest;

import org.aileen.mod.datasource.config.AileenMybatisConfig;
import org.aileen.mod.datasource.utils.AileenBeanUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class}, scanBasePackages = {"org.aileen"})
public class DataSourceSetTestApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(org.aileen.mod.datasource.DataSourceApp.class, args);
        AileenMybatisConfig aileenMybatisConfig = context.getBean(AileenMybatisConfig.class);
        aileenMybatisConfig.getDataSourceNames();
        AileenBeanUtils aileenBeanUtils = context.getBean(AileenBeanUtils.class);
    }
}
