package com.javacodingskills.spring.batch.demo1.job.kafka;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.javacodingskills.spring.batch.demo1.model.Employee;
import com.javacodingskills.spring.batch.demo1.sender.Sender;

public class EmployeeKafkaSender implements ItemWriter<Employee> {
	@Autowired
	private Sender sender;

	@Override
	public void write(List<? extends Employee> items) throws Exception {
		// TODO Auto-generated method stub
		for(Employee employee:items)
		{
			sender.send(employee);
		}
		System.out.println("kafkaka message send");
		
	}

}
