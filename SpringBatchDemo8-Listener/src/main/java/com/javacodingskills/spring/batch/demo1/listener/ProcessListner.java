package com.javacodingskills.spring.batch.demo1.listener;

import org.springframework.batch.core.ItemProcessListener;

import com.javacodingskills.spring.batch.demo1.dto.EmployeeDTO;
import com.javacodingskills.spring.batch.demo1.model.Employee;

//import javax.batch.api.chunk.listener.ItemProcessListener;

public class ProcessListner implements ItemProcessListener<EmployeeDTO,Employee> {

	@Override
	public void beforeProcess(EmployeeDTO item) {
		// TODO Auto-generated method stub
		System.out.println("before process "+item.toString());
		
	}

	@Override
	public void afterProcess(EmployeeDTO item, Employee result) {
		// TODO Auto-generated method stub
		System.out.println("after process "+result.toString());
		
	}

	@Override
	public void onProcessError(EmployeeDTO item, Exception e) {
		// TODO Auto-generated method stub
		System.out.println("on error in process "+e.getMessage());
		
	}

	
}
