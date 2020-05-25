package com.javacodingskills.spring.batch.demo1.writer;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javacodingskills.spring.batch.demo1.model.Employee;
import com.javacodingskills.spring.batch.demo1.repo.EmployeeRepository;
@Component
public class CustomDBWriter implements ItemWriter<Employee> {
	private static final Logger logger=LogManager.getLogger(CustomDBWriter.class);
	@Autowired
	private EmployeeRepository employeeRepo;

	@Override
	public void write(List<? extends Employee> employee) throws Exception {
		// TODO Auto-generated method stub
		employeeRepo.saveAll(employee);
		logger.info("csv file submitted in database successfully "+employee.size());
		
	}

}
