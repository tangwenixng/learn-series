package com.twx.learn.sc.consumer;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @EnableEurekaClient: enable Eureka discovery configuration
 */
@EnableEurekaClient
@SpringBootApplication
public class EurekaConsumerApplication {

    public static void main(String[] args) {
//        SpringApplication.run(EurekaConsumerApplication.class, args);
        new SpringApplicationBuilder(EurekaConsumerApplication.class).web(WebApplicationType.SERVLET).run(args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
