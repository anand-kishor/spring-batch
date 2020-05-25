package com.javacodingskills.spring.batch.demo1.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.javacodingskills.spring.batch.demo1.repo.EmployeeRepo;

public class DataCleanup implements Tasklet {
	@Autowired
	private EmployeeRepo employeeRepo;
	

	public DataCleanup(EmployeeRepo employeeRepo) {
		super();
		this.employeeRepo = employeeRepo;
	}


	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		// TODO Auto-generated method stub
		employeeRepo.deleteAll();
		return RepeatStatus.FINISHED;
	}

}
