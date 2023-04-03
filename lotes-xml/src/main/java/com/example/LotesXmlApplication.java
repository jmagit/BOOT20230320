package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.batch.core.repository.support.SimpleJobRepository;

@SpringBootApplication
public class LotesXmlApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LotesXmlApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:job.xml");
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("csvConverterJob");
		try {
			JobParameters jobParameters = new JobParametersBuilder()
					.addLong("time",System.currentTimeMillis()).toJobParameters();
			JobExecution execution = jobLauncher.run(job, jobParameters);
			System.out.println("Exit Status : " + execution.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
