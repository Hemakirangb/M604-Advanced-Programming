package com.job.application.tracker.controller;


import com.job.application.tracker.entity.JobApplication;
import com.job.application.tracker.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping
    public JobApplication addJob(@RequestBody JobApplication job) {
        return jobService.addJob(job);
    }

    @GetMapping
    public List<JobApplication> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/user/{userId}")
    public List<JobApplication> getJobsByUser(@PathVariable Long userId) {
        return jobService.getJobsByUser(userId);
    }

    @PutMapping("/{id}")
    public JobApplication updateJob(@PathVariable Long id, @RequestBody JobApplication job) {
        job.setId(id);
        return jobService.updateJob(job);
    }

    @DeleteMapping("/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return "Deleted Successfully";
    }
}