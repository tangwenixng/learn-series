package com.twx.learn.learnspringexpansion;

import com.twx.learn.learnspringexpansion.importbean.EnablePerson;
import com.twx.learn.learnspringexpansion.importbean.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnablePerson
public class LearnSpringExpansionApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LearnSpringExpansionApplication.class, args);
        Person person = (Person) context.getBean("person");
        System.out.println("person=>"+person);
        String token = context.getEnvironment().getProperty("app.token");
        System.out.println("token=>"+token);
    }
}
