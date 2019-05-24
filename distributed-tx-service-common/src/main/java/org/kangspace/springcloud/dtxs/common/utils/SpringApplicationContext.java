package org.kangspace.springcloud.dtxs.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 2019/5/17 17:35
 *
 * @author kango2ger@gmail.com
 */
public class SpringApplicationContext implements ApplicationContextAware{
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public <T> T getBean(Class<T> t){
        return applicationContext.getBean(t);
    }
}
