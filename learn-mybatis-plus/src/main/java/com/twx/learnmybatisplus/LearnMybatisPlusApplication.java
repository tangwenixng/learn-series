package com.twx.learnmybatisplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration()
public class LearnMybatisPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnMybatisPlusApplication.class, args);
	}
}
