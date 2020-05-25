package com.javacodingskills.spring.batch.demo1.job;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.javacodingskills.spring.batch.demo1.dto.EmployeeDTO;
import com.javacodingskills.spring.batch.demo1.model.Employee;

public   class EmailSenderWriter implements ItemWriter<EmployeeDTO> {

	public void write(List<? extends EmployeeDTO> items) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("email send to all employee successfully");
		
	}

	

}
