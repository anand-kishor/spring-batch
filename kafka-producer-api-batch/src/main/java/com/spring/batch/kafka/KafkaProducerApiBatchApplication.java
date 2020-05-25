package com.spring.batch.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"com.spring.batch.kafka"})
public class KafkaProducerApiBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApiBatchApplication.class, args);
	}

}
