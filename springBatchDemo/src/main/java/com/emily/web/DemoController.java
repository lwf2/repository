package com.emily.web;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	Job importJob;
	
	public JobParameters jobParameters;
	
	@RequestMapping("/imp")
	public String imp(String fileName) throws Exception{
		String path = fileName + ".txt";
		jobParameters = new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis())
				.addString("input.file.name", path)
				.toJobParameters();
		jobLauncher.run(importJob, jobParameters);
		return "OK";
	}
	
}