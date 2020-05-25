package com.javacodingskills.spring.batch.demo1;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;
@EnableBatchProcessing
@ComponentScan(basePackages={"com.javacodingskills.spring.batch.demo1"})
@SpringBootApplication
@EnableKafka
public class SpringBatchDemo10Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchDemo10Application.class, args);
	}

}
