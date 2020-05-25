package com.javacodingskills.spring.batch.demo1.listener;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.StepListener;

import com.javacodingskills.spring.batch.demo1.dto.EmployeeDTO;

public class ReaderListener implements ItemReadListener<EmployeeDTO>, StepListener {

	@Override
	public void beforeRead() {
		// TODO Auto-generated method stub
		System.out.println("before read operation");
	}

	@Override
	public void afterRead(EmployeeDTO item) {
		// TODO Auto-generated method stub
		System.out.println("after read operation "+item.toString());
		
	}

	@Override
	public void onReadError(Exception ex) {
		// TODO Auto-generated method stub
		System.out.println("on error while reading "+ex.getMessage());
		
	}

}
