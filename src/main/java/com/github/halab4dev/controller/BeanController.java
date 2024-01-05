package com.github.halab4dev.controller;

import com.github.halab4dev.request.BeanInfo;
import com.github.halab4dev.request.UpdateBeansRequest;
import com.github.halab4dev.util.BeanFactory;
import com.github.halab4dev.util.BeanUpdater;
import com.github.halab4dev.util.ExternalJarFileClassLoader;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/beans")
public class BeanController {

    private final BeanFactory beanFactory;
    private final BeanUpdater beanUpdater;

    @PutMapping
    public String load(@RequestBody UpdateBeansRequest request) throws Exception {
        String jarFilePath = request.getJarFilePath();
        List<BeanInfo> beansInfo = request.getBeans();

        List<String> targetClasses = beansInfo.stream().map(BeanInfo::getTargetClassName).collect(Collectors.toList());
        Map<String, Class<?>> classes = ExternalJarFileClassLoader.loadClasses(jarFilePath, targetClasses);

        for (BeanInfo beanInfo : beansInfo) {
            Class<?> clazz = classes.get(beanInfo.getTargetClassName());
            Object instance = beanFactory.createBeanInstance(clazz, beanInfo.getConstructorParameterTypes());
            beanUpdater.updateBean(beanInfo.getBeanName(), instance);
        }
        return "OK";
    }
}
