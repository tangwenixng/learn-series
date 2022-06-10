package com.twx.learn.learnspringbootnacos;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.alibaba.nacos.spring.context.annotation.discovery.EnableNacosDiscovery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@NacosPropertySource(dataId = "learn-nacos", autoRefreshed = true, type = ConfigType.YAML)
public class LearnSpringBootNacosApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LearnSpringBootNacosApplication.class, args);
    }

    @NacosInjected
    private NamingService namingService;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.port}")
    private Integer port;

    @Override
    public void run(String... args) throws Exception {
        namingService.registerInstance(applicationName, "127.0.0.1", port);
    }
}
