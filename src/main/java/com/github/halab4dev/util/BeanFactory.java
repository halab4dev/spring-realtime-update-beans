package com.github.halab4dev.util;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class BeanFactory {

    private final ApplicationContext applicationContext;

    public Object createBeanInstance(Class<?> clazz, List<String> constructorParameterTypes) throws Exception {
        Class<?>[] constructorParameterClasses = getConstructorParameterClasses(constructorParameterTypes);
        Object[] constructorParameters = getConstructorParameterBeans(applicationContext, constructorParameterClasses);

        Constructor<?> constructor = clazz.getConstructor(constructorParameterClasses);
        return constructor.newInstance(constructorParameters);
    }

    private Class<?>[] getConstructorParameterClasses(List<String> constructorParameterTypes) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        for (String className : constructorParameterTypes) {
            Class<?> clazz = Class.forName(className);
            classes.add(clazz);
        }
        return classes.toArray(new Class[0]);
    }

    private Object[] getConstructorParameterBeans(ApplicationContext applicationContext, Class<?>[] constructorParameterClasses) {
        List<Object> parameters = new ArrayList<>();
        for (Class<?> clazz : constructorParameterClasses) {
            Object bean = applicationContext.getBean(clazz);
            parameters.add(bean);
        }
        return parameters.toArray(new Object[0]);
    }
}
