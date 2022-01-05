package com.twx.learn.sc.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @EnableEurekaClient: enable Eureka discovery configuration
 */
@EnableEurekaClient
@SpringBootApplication
public class EurekaRibbonApplication {

    public static void main(String[] args) {
//        SpringApplication.run(EurekaConsumerApplication.class, args);
        new SpringApplicationBuilder(EurekaRibbonApplication.class).web(WebApplicationType.SERVLET).run(args);
    }

    /**
     * @LoadBalanced: use a LoadBalancerClient.
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
