package com.job.application.tracker.repository;

import com.job.application.tracker.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface JobRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUserId(Long userId);
}