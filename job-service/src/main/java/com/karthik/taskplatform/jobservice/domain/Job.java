package com.karthik.taskplatform.jobservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.OffsetDateTime;


@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
public class Job
{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(nullable = false)
    private String userId;


    @Column(nullable = false)
    private String type;


    @Lob
    @Column(nullable = false)
    private String payload;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status;


    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant startedAt;

    private Instant finishedAt;

    private String failureReason;


    @PrePersist
    public void onCreate()
    {
        this.createdAt = Instant.now();
    }


    public void markFinished() {
        this.status = JobStatus.FINISHED;
        this.finishedAt = Instant.now();
    }

    public void markFailed(String reason) {
        this.status = JobStatus.FAILED;
        this.failureReason = reason;
        this.finishedAt = Instant.now();
    }

}
