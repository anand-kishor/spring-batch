package com.spring.batch.kafka.job;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring.batch.kafka.model.Employee;
import com.spring.batch.kafka.sender.Sender;

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
