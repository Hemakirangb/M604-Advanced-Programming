package com.job.application.tracker.service;


import com.job.application.tracker.entity.JobApplication;
import com.job.application.tracker.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public JobApplication addJob(JobApplication job) {
        return jobRepository.save(job);
    }

    public List<JobApplication> getAllJobs() {
        return jobRepository.findAll();
    }

    public List<JobApplication> getJobsByUser(Long userId) {
        return jobRepository.findByUserId(userId);
    }

    public JobApplication updateJob(JobApplication job) {
        return jobRepository.save(job);
    }

    public void deleteJob(Long jobId) {
        jobRepository.deleteById(jobId);
    }
}