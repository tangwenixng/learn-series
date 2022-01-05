package com.twx.learn.sc.configserver;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 *
 */
@EnableEurekaClient
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

    public static void main(String[] args) {
//        SpringApplication.run(EurekaConsumerApplication.class, args);
        new SpringApplicationBuilder(ConfigServerApplication.class).web(WebApplicationType.SERVLET).run(args);
    }

}
