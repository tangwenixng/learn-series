package com.twx.learn.learnspringexpansion.environment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MyEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        /*Properties properties = new Properties();
        try {
            String path = System.getProperty("user.dir") + File.separator + "env" + File.separator + "soyuan.properties";
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PropertiesPropertySource soyuan = new PropertiesPropertySource("soyuan", properties);
        environment.getPropertySources().addLast(soyuan);*/

        //加载自定义yaml
        YmlResourceFactory factory = new YmlResourceFactory();
        String yamlPath = System.getProperty("user.dir") + File.separator + "env" + File.separator + "soyuan.yml";
        try {
            PropertySource soyuan = factory.createPropertySource("soyuan", new EncodedResource(new FileSystemResource(yamlPath)));
            environment.getPropertySources().addLast(soyuan);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
