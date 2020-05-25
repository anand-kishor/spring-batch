package com.javacodingskills.spring.batch.demo1.job.jdbcmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.javacodingskills.spring.batch.demo1.model.Employee;

public class EmployeeRowMapper implements RowMapper<Employee> {

	@Override
	public Employee mapRow(ResultSet rs, int i) throws SQLException {
		// TODO Auto-generated method stub
		Employee employee=new Employee();
		employee.setEmployeeId(rs.getString("employee_id"));
		employee.setFirstName(rs.getString("first_name"));
		employee.setLastName(rs.getString("last_name"));
		employee.setEmail(rs.getString("email"));
		employee.setAge(rs.getString("age"));
		return employee;
	}

}
