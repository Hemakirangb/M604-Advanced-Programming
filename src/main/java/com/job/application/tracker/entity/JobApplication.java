package com.job.application.tracker.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "job_applications")
public class JobApplication {
//
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Id
//    @Column(name = "id", nullable = false)
//    private Long id;

    private String position;

    private String status; // e.g., Applied, Interview, Offer, Rejected

    private LocalDate appliedDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
