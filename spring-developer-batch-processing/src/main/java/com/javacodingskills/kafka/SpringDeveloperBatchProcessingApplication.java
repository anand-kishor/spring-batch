package com.javacodingskills.kafka;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableBatchProcessing
@SpringBootApplication
public class SpringDeveloperBatchProcessingApplication {
	

	public static void main(String[] args) {
		String[] newArgs=new String[] {"inputFlatFile=classpath:/data/csv/bigtransactions3.csv"};
	
		
		SpringApplication.run(SpringDeveloperBatchProcessingApplication.class,newArgs );
	}

}
