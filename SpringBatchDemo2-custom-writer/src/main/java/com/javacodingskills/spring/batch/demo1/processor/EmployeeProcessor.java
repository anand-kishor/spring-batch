package com.javacodingskills.spring.batch.demo1.processor;

import org.springframework.batch.item.ItemProcessor;


import com.javacodingskills.spring.batch.demo1.dto.EmployeeDTO;
import com.javacodingskills.spring.batch.demo1.model.Employee;

public class EmployeeProcessor implements ItemProcessor<EmployeeDTO,Employee> {

	@Override
	public Employee process(EmployeeDTO item) throws Exception {
		// TODO Auto-generated method stub
		if(!isValid(item))
		{
			return null;
		}
		Employee employee=new Employee();
		employee.setEmployeeId(item.getEmployeeId());
		employee.setFirstName(item.getFirstName());
		employee.setLastName(item.getLastName());
		employee.setEmail(item.getEmail());
		employee.setAge(item.getAge());
		System.out.println("inside processor "+employee.toString());
		return employee;
	}
	boolean isValid(EmployeeDTO item)
	{
		return (item.getFirstName().startsWith("AAA")?false :true);
	}

}
