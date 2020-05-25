package com.javacodingskills.spring.batch.demo1.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		System.out.println("before job execution "+jobExecution.getJobInstance().getJobName()+" Execution");
		jobExecution.getExecutionContext().putString("beforejob", "beforeValue");

	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		System.out.println("after job execution "+jobExecution.getExecutionContext().getString("beforejob"));

		if(jobExecution.getStatus().equals(BatchStatus.COMPLETED))
		{
			System.out.println("success");
		}
		else {
			System.out.println("job faield");
		}
	}

}
