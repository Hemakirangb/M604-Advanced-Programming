package com.example.JobApplicationPortal.repository;


import com.example.JobApplicationPortal.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {


    List<JobApplication> findByStatus(String status);

    List<JobApplication> findByCompanyContainingIgnoreCase(String company);

    List<JobApplication> findByLocationContainingIgnoreCase(String location);

    @Query("SELECT j.status AS status, COUNT(j) AS count FROM JobApplication j GROUP BY j.status")
    List<Object[]> countByStatus();

    @Query("SELECT j FROM JobApplication j WHERE " +
            "LOWER(j.company) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.position) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.notes) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<JobApplication> searchByKeyword(@Param("keyword") String keyword);

    @Query(value = "SELECT status, COUNT(*) AS total FROM job_applications GROUP BY status ORDER BY total DESC",
            nativeQuery = true)
    List<Object[]> getStatusSummary();
}