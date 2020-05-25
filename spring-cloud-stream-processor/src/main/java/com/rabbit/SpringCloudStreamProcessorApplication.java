package com.rabbit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;
@EnableBinding(Processor.class)
@SpringBootApplication
public class SpringCloudStreamProcessorApplication {
	
	@Transformer(inputChannel=Processor.INPUT,outputChannel=Processor.OUTPUT)
	public Object timeToTransform(Long date)
	{
		DateFormat formate=new SimpleDateFormat("dd/MM/yyyy");
		return formate.format(date);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamProcessorApplication.class, args);
	}

}
