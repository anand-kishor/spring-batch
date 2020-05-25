package com.javacodingskills.spring.batch.demo1.processor;

import java.util.Random;

import org.springframework.batch.item.ItemProcessor;


import com.javacodingskills.spring.batch.demo1.dto.EmployeeDTO;
import com.javacodingskills.spring.batch.demo1.model.Employee;

public class EmployeeProcessor implements ItemProcessor<EmployeeDTO,Employee> {

	@Override
	public Employee process(EmployeeDTO item) throws Exception {
		// TODO Auto-generated method stub
		Employee employee=new Employee();
		//employee.setEmployeeId(item.getEmployeeId());
		employee.setEmployeeId(item.getEmployeeId()+new Random().nextInt(1000000));
		employee.setFirstName(item.getFirstName());
		employee.setLastName(item.getLastName());
		employee.setEmail(item.getEmail());
		employee.setAge(item.getAge());
		System.out.println("inside processor "+employee.toString());
		return employee;
	}

}
