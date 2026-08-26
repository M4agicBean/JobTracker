package com.jobtracker.app.interview;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview,Long> {
    List<Interview> findByApplicationIdOrderByScheduledAtAsc(Long applicationId);
    List<Interview> findByScheduledAtBetween(Instant from, Instant to);
}
