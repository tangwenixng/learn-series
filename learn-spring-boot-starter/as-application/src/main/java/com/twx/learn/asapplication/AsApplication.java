package com.twx.learn.asapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsApplication.class, args);
        /*SpringApplication application = new SpringApplication(AsApplication.class);
        application.addInitializers(new MyApplicationContextInitializer());
        application.run(args);*/
    }
}
