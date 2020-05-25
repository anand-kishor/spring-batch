package com.javacodingskills.kafka;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDeveloperBatchProcessingApplication {
	

	public static void main(String[] args) {
		String[] newArgs=new String[] {"inputFlatFile=classpath:/data/csv/bigtransactions4.csv"};
	
		
		SpringApplication.run(SpringDeveloperBatchProcessingApplication.class,newArgs );
	}

}
