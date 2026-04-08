package com.example.JobApplicationPortal.service;


import com.example.JobApplicationPortal.dto.JobApplicationDTO;
import com.example.JobApplicationPortal.exception.ResourceNotFoundException;
import com.example.JobApplicationPortal.model.JobApplication;
import com.example.JobApplicationPortal.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobApplicationService {

    private final JobApplicationRepository repository;

    private static final List<String> VALID_STATUSES =
            List.of("APPLIED", "INTERVIEW", "OFFER", "REJECTED", "WITHDRAWN");

    // CREATE
    @Transactional
    public JobApplicationDTO create(JobApplicationDTO dto) {
        validateStatus(dto.getStatus());
        JobApplication entity = toEntity(dto);
        JobApplication saved = repository.save(entity);
        log.info("Created job application with id={}", saved.getId());
        return toDTO(saved);
    }

    // READ ALL
    public List<JobApplicationDTO> findAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    // READ BY ID
    public JobApplicationDTO findById(Long id) {
        JobApplication entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found with id: " + id));
        return toDTO(entity);
    }

    // READ BY STATUS
    public List<JobApplicationDTO> findByStatus(String status) {
        validateStatus(status);
        return repository.findByStatus(status.toUpperCase()).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    // SEARCH
    public List<JobApplicationDTO> search(String keyword) {
        return repository.searchByKeyword(keyword).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    // UPDATE
    @Transactional
    public JobApplicationDTO update(Long id, JobApplicationDTO dto) {
        JobApplication existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found with id: " + id));
        validateStatus(dto.getStatus());

        existing.setCompany(dto.getCompany());
        existing.setPosition(dto.getPosition());
        existing.setStatus(dto.getStatus().toUpperCase());
        existing.setAppliedDate(dto.getAppliedDate());
        existing.setNotes(dto.getNotes());
        existing.setSalaryRange(dto.getSalaryRange());
        existing.setLocation(dto.getLocation());

        JobApplication updated = repository.save(existing);
        log.info("Updated job application id={}", id);
        return toDTO(updated);
    }

    // DELETE
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Job application not found with id: " + id);
        }
        repository.deleteById(id);
        log.info("Deleted job application id={}", id);
    }

    // AGGREGATION — status summary
    public Map<String, Long> getStatusSummary() {
        List<Object[]> results = repository.countByStatus();
        Map<String, Long> summary = new LinkedHashMap<>();
        for (Object[] row : results) {
            summary.put((String) row[0], (Long) row[1]);
        }
        return summary;
    }

    // Validation
    private void validateStatus(String status) {
        if (status == null || !VALID_STATUSES.contains(status.toUpperCase())) {
            throw new IllegalArgumentException("Invalid status. Must be one of: " + VALID_STATUSES);
        }
    }

    // Mapping helpers
    private JobApplicationDTO toDTO(JobApplication e) {
        return JobApplicationDTO.builder()
                .id(e.getId()).company(e.getCompany()).position(e.getPosition())
                .status(e.getStatus()).appliedDate(e.getAppliedDate())
                .notes(e.getNotes()).salaryRange(e.getSalaryRange()).location(e.getLocation())
                .build();
    }

    private JobApplication toEntity(JobApplicationDTO dto) {
        return JobApplication.builder()
                .company(dto.getCompany()).position(dto.getPosition())
                .status(dto.getStatus().toUpperCase()).appliedDate(dto.getAppliedDate())
                .notes(dto.getNotes()).salaryRange(dto.getSalaryRange()).location(dto.getLocation())
                .build();
    }
}
