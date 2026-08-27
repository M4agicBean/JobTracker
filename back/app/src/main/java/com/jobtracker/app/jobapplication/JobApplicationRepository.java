package com.jobtracker.app.jobapplication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {

    @Override
    @EntityGraph(attributePaths = {"company"})
    Page<JobApplication> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"company", "techTags"})
    List<JobApplication> findAllByStatus(ApplicationStatus status);

    boolean existsByCompanyIdAndPositionIgnoreCase(Long companyId, String position);
}
