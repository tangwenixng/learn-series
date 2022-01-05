package com.twx.learn.sc.configclient;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 *
 */
@EnableEurekaClient
@SpringBootApplication
public class ConfigClientApplication {

    public static void main(String[] args) {
//        SpringApplication.run(EurekaConsumerApplication.class, args);
        new SpringApplicationBuilder(ConfigClientApplication.class).web(WebApplicationType.SERVLET).run(args);
    }

}
