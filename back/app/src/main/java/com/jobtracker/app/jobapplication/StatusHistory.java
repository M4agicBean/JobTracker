package com.jobtracker.app.jobapplication;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;

@Entity
@Table(name = "status_history")
@Getter
public class StatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ApplicationStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ApplicationStatus newStatus;

    @Column(updatable = false, nullable = false)
    private Instant changedAt =  Instant.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_application_id", nullable = false)
    private JobApplication application;

    protected StatusHistory() {}

    public StatusHistory(ApplicationStatus oldStatus, ApplicationStatus newStatus, JobApplication application) {
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.application = application;
    }
}
