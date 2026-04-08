package com.example.JobApplicationPortal.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplicationDTO {
    private Long id;
    private String company;
    private String position;
    private String status;
    private LocalDate appliedDate;
    private String notes;
    private String salaryRange;
    private String location;
}