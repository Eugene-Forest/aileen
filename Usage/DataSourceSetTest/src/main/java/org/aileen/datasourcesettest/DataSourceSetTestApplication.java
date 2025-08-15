package org.aileen.datasourcesettest;

import org.aileen.datasourcesettest.client.MssqlTestService;
import org.aileen.datasourcesettest.mapper.dbms.MssqlTestMapper;
import org.aileen.datasourcesettest.service.MssqlTestServiceImpl;
import org.aileen.mod.datasource.utils.DataSourceBeanDefinitionRegister;
import org.aileen.mod.datasource.dynamic.DynamicDataSource;
import org.aileen.mod.datasource.utils.AileenBeanUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class}, scanBasePackages = {"org.aileen"})
public class DataSourceSetTestApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DataSourceSetTestApplication.class, args);
//        DataSourceBeanDefinitionRegister dataSourceBeanDefinitionRegister = context.getBean(DataSourceBeanDefinitionRegister.class);
//        dataSourceBeanDefinitionRegister.getDataSourceNames();
        AileenBeanUtils aileenBeanUtils = context.getBean(AileenBeanUtils.class);
        String[] datasourceNames = context.getBeanNamesForType(DynamicDataSource.class);
        for (String datasourceName : datasourceNames){
            DynamicDataSource dynamicDataSource = aileenBeanUtils.getBean(datasourceName, DynamicDataSource.class);
            System.out.println(dynamicDataSource.getDataSourceName());
        }
        MssqlTestMapper mapper = aileenBeanUtils.getBean(MssqlTestMapper.class);
        System.out.println("mapper bean has create!");
        MssqlTestService mssqlService = aileenBeanUtils.getBean(MssqlTestServiceImpl.class);
        System.out.println("mssqlService bean has create!");
    }
}
