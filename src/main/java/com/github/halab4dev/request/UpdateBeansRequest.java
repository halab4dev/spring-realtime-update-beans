package com.github.halab4dev.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateBeansRequest {

    private String jarFilePath;
    private List<BeanInfo> beans;
}
