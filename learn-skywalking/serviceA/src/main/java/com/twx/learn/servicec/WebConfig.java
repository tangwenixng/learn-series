package com.twx.learn.servicec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {
    @Bean
    public RestTemplate restTemplate() {
        //new HttpComponentsClientHttpRequestFactory()
        return new RestTemplate();
    }
}
