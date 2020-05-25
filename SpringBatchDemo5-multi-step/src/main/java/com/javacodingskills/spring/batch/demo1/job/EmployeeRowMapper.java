package com.javacodingskills.spring.batch.demo1.job;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.javacodingskills.spring.batch.demo1.dto.EmployeeDTO;
import com.javacodingskills.spring.batch.demo1.model.Employee;

public class EmployeeRowMapper implements RowMapper<Employee> {

	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Employee employee=new Employee();
		employee.setEmployeeId(rs.getString("employee_Id"));
		employee.setFirstName(rs.getString("first_Name"));
		employee.setLastName(rs.getString("last_Name"));
		employee.setEmail(rs.getString("email"));
		try {
		employee.setAge(rs.getString("age"));
		}
		catch(Exception e)
		{
			
		}
		return employee;
	}

}
