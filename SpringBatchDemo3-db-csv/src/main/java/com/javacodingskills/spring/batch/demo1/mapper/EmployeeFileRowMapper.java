package com.javacodingskills.spring.batch.demo1.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.javacodingskills.spring.batch.demo1.dto.EmployeeDTO;
import com.javacodingskills.spring.batch.demo1.model.Employee;

public class EmployeeFileRowMapper implements FieldSetMapper<EmployeeDTO> {

	@Override
	public EmployeeDTO mapFieldSet(FieldSet fieldSet) throws BindException {
		// TODO Auto-generated method stub
		EmployeeDTO employee=new EmployeeDTO();
		employee.setEmployeeId(fieldSet.readString("employeeId"));
		employee.setFirstName(fieldSet.readString("firstName"));
		employee.setLastName(fieldSet.readString("lastName"));
		employee.setEmail(fieldSet.readString("email"));
		try {
		employee.setAge(fieldSet.readString("age"));
		}
		catch(Exception e)
		{
			
		}
		return employee;
	}

}
