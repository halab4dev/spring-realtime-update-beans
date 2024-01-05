package com.github.halab4dev.request;

import lombok.Data;

import java.util.List;

@Data
public class BeanInfo {

    private String beanName;
    private String targetClassName;
    private List<String> constructorParameterTypes;
}
