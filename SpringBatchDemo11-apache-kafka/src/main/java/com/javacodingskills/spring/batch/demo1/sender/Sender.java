package com.javacodingskills.spring.batch.demo1.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.javacodingskills.spring.batch.demo1.model.Employee;

@Service
public class Sender {
	
	@Autowired
	private KafkaTemplate<String,Employee> kafkaTemplate;
	public void send(Employee employee)
	{
		kafkaTemplate.send("EMPLOYEE", employee);
	}

}
