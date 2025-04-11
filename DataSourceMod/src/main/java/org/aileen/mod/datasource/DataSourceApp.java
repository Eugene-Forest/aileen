package org.aileen.mod.datasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class}, scanBasePackages = {"org.aileen"})
public class DataSourceApp {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DataSourceApp.class, args);
    }
}
