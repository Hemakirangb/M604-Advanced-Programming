package com.job.application.tracker;

import com.job.application.tracker.entity.Company;
import com.job.application.tracker.entity.JobApplication;
import com.job.application.tracker.repository.CompanyRepository;
import com.job.application.tracker.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private JobRepository jobApplicationRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Test data
		Company company = new Company();
		company.setName("Tech Corp");
		companyRepository.save(company);

		JobApplication job = new JobApplication();
		job.setPosition("Software Engineer");
		job.setStatus("Applied");
		job.setAppliedDate(LocalDate.now());
		job.setCompany(company);
		jobApplicationRepository.save(job);

		System.out.println("Job Applications: " + jobApplicationRepository.findAll());
	}
}