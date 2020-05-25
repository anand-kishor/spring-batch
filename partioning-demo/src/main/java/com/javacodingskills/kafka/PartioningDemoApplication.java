package com.javacodingskills.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
@EnableBatchIntegration
public class PartioningDemoApplication {

	public static void main(String[] args) {
		List<String> strings=Arrays.asList(args);
		List<String> finalArgs=new ArrayList<>(strings.size()+1);
		finalArgs.add("inputFiles=classpath:/data/csv/transaction*.csv");
		SpringApplication.run(PartioningDemoApplication.class, finalArgs.toArray(new String[finalArgs.size()]));
	}

}
