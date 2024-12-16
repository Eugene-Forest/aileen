package org.aileen.mod.datasource.units;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Eugene-Forest
 * {@code @date} 2024/11/19
 */
public class TCBeanUnit implements ApplicationContextAware{

    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
