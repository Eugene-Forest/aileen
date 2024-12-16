package org.aileen.mod.common.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @author qaz22
 * {@code @date} 2024/11/10
 * {@code @project} tutor-selenium
 */
public class TransactionBeanCrater {

    @Bean
    public DataSourceTransactionManager createTransactionMangerBean(){
        return new DataSourceTransactionManager();
    }
}
