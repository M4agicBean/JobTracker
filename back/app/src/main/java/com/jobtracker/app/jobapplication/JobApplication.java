package com.jobtracker.app.jobapplication;

import com.jobtracker.app.company.Company;
import com.jobtracker.app.techtag.TechTag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "job_application")
@Getter
@Setter
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApplicationStatus status = ApplicationStatus.WISHLIST;

    @Column(nullable = false)
    private String position;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SeniorityLevel seniorityLevel;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private WorkMode workMode;

    @Column(length = 3)
    private String salaryCurrency = "PLN";

    private Integer salaryMin;
    private Integer salaryMax;

    private String offerUrl;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "job_application_tech_tag",
            joinColumns = @JoinColumn(name = "job_application_id"),
            inverseJoinColumns = @JoinColumn(name = "tech_tags_id")
    )
    private Set<TechTag> techTags =  new HashSet<TechTag>();

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    private LocalDate appliedAt;

    @Version
    private Long version;

    protected JobApplication() {}

    public JobApplication(String position, Company company) {
        this.position = position;
        this.company = company;
    }

}
