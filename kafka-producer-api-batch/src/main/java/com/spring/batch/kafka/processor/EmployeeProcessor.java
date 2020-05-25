package com.spring.batch.kafka.processor;

import org.springframework.batch.item.ItemProcessor;

import com.spring.batch.kafka.dto.EmployeeDTO;
import com.spring.batch.kafka.model.Employee;

public class EmployeeProcessor implements ItemProcessor<EmployeeDTO,Employee> {

	@Override
	public Employee process(EmployeeDTO item) throws Exception {
		// TODO Auto-generated method stub
		Employee employee=new Employee();
		employee.setEmployeeId(item.getEmployeeId());
		employee.setFirstName(item.getFirstName());
		employee.setLastName(item.getLastName());
		employee.setEmail(item.getEmail());
		employee.setAge(item.getAge());
		System.out.println("inside processor "+employee.toString());
		return employee;
	}

}
