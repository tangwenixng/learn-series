package com.twx.learn.sc.feign;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @EnableEurekaClient: enable Eureka discovery configuration
 * @EnableFeignClients :Scans for interfaces that declare they are feign clients (via FeignClient @FeignClient).
 */
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class EurekaFeignApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaFeignApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
