package com.javacodingskills.spring.batch.demo1;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@EnableBatchProcessing
@ComponentScan(basePackages={"com.javacodingskills.spring.batch.demo1"})
@SpringBootApplication
public class SpringBatchDemo4Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchDemo4Application.class, args);
	}

}
