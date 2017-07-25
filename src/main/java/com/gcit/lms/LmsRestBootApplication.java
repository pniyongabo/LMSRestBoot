package com.gcit.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
@ComponentScan
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class LmsRestBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(LmsRestBootApplication.class, args);
	}
}
