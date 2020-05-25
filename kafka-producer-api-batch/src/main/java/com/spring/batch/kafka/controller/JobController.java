package com.spring.batch.kafka.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.batch.kafka.runner.JobRunner;

@RestController
@RequestMapping("/run")
public class JobController {
	
	private JobRunner jobRunner;
	@Autowired
	public JobController(JobRunner jobRunner) {
		super();
		this.jobRunner = jobRunner;
	}

	@RequestMapping(value="/job")
	public String welcome()
	{
		jobRunner.runBatchJob();
		return String.format("job demo1 is successfully submitted");
	}

}
