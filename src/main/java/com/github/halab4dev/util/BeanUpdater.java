package com.github.halab4dev.util;

import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeanUpdater {

    private final ApplicationContext applicationContext;
    private final DefaultSingletonBeanRegistry beanFactory;

    public BeanUpdater(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.beanFactory = (DefaultSingletonBeanRegistry) applicationContext.getAutowireCapableBeanFactory();
    }

    public void updateBean(String beanName, Object newInstance) {
        String oldBean = applicationContext.getBean(beanName).toString();
        beanFactory.destroySingleton(beanName);
        beanFactory.registerSingleton(beanName, newInstance);
        String newBean = applicationContext.getBean(beanName).toString();
        System.out.println(String.format("Replaced %s bean from instance %s to %s", beanName, oldBean, newBean));
    }
}
