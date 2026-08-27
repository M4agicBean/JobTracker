package com.jobtracker.app.interview;


import com.jobtracker.app.jobapplication.JobApplication;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "interview")
@Getter
@Setter
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Instant scheduledAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InterviewType type;

    @Column(length = 2000)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_application_id", nullable = false)
    private JobApplication application;


    protected Interview() {}

    public Interview(Instant scheduledAt, InterviewType type, JobApplication application) {
        this.scheduledAt = scheduledAt;
        this.type = type;
        this.application = application;
    }

}
