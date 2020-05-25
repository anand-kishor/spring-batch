package com.javacodingskills.spring.batch.demo1.listener;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;

import com.javacodingskills.spring.batch.demo1.model.Employee;

public class WriterListener implements ItemWriteListener<Employee> {

	@Override
	public void beforeWrite(List<? extends Employee> items) {
		// TODO Auto-generated method stub
		System.out.println("before write: ");
		items.stream().forEach(System.out::println);
		
	}

	@Override
	public void afterWrite(List<? extends Employee> items) {
		// TODO Auto-generated method stub
		System.out.println("after write ");
		
	}

	@Override
	public void onWriteError(Exception exception, List<? extends Employee> items) {
		// TODO Auto-generated method stub
		System.out.println("on write error :"+exception.getMessage());
		
	}

}
