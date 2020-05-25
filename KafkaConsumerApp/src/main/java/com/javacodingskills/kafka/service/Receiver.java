package com.javacodingskills.kafka.service;

import java.util.concurrent.CountDownLatch;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.javacodingskills.kafka.model.Employee;

@Component
public class Receiver {
	private CountDownLatch latch=new CountDownLatch(1);
	@KafkaListener(topics="EMPLOYEE")
	public void receive(Employee employee)
	{
		System.out.println("employee data is received "+employee.toString());
		latch.countDown();
	}
	

}
