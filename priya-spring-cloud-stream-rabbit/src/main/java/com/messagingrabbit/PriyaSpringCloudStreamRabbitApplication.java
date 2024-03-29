package com.messagingrabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import com.messagingrabbit.model.Employee;
@EnableBinding(Sink.class)
@SpringBootApplication
public class PriyaSpringCloudStreamRabbitApplication {

	//String INPUT="employee-channel";
	public static void main(String[] args) {
		SpringApplication.run(PriyaSpringCloudStreamRabbitApplication.class, args);
	}
	@StreamListener(Sink.INPUT)
	public void getMessage(Employee employee)
	{
		System.out.println(employee.toString());
		//return "publish message";
	}

}
