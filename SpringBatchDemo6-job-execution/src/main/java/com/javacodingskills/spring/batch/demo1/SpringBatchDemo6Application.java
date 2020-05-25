package com.javacodingskills.spring.batch.demo1;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@EnableBatchProcessing
@ComponentScan(basePackages={"com.javacodingskills.spring.batch.demo1"})
@SpringBootApplication
public class SpringBatchDemo6Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchDemo6Application.class, args);
	}

}
