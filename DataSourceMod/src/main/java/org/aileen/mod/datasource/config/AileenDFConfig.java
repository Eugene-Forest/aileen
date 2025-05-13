package org.aileen.mod.datasource.config;

import org.aileen.mod.datasource.loader.AccountSetDataLoader;
import org.aileen.mod.datasource.model.DataSourceSet;
import org.aileen.mod.datasource.utils.AileenBeanUtils;
import org.aileen.mod.datasource.utils.DataSourceBeanDefinitionRegister;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//public class AileenDFConfig {
//
//    @ConditionalOnMissingBean(DataSourceBeanDefinitionRegister.class)
//    @Bean
//    //指定beanName
//    public AileenBeanUtils aileenBeanUtils(ApplicationContext applicationContext){
//        return new AileenBeanUtils(applicationContext);
//    }
//
//    @ConditionalOnMissingBean(DataSourceBeanDefinitionRegister.class)
//    @Bean
//    public DataSourceSet dataSourceSet(){
//        return new DataSourceSet();
//    }
//
//    @ConditionalOnMissingBean(DataSourceBeanDefinitionRegister.class)
//    @Bean
//    public AccountSetDataLoader accountSetDataLoader(DataSourceSet dataSourceSet){
//        return new AccountSetDataLoader(dataSourceSet);
//    }
//}
