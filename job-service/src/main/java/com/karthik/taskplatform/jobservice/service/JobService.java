package com.karthik.taskplatform.jobservice.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.karthik.taskplatform.jobservice.domain.Job;
import com.karthik.taskplatform.jobservice.domain.JobStatus;
import com.karthik.taskplatform.jobservice.event.JobCreatedEvent;
import com.karthik.taskplatform.jobservice.event.JobEventPublisher;
import com.karthik.taskplatform.jobservice.outbox.OutboxEvent;
import com.karthik.taskplatform.jobservice.outbox.OutboxEventRepository;
import com.karthik.taskplatform.jobservice.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JobService
{
    private final JobRepository jobRepository;
    private final JobEventPublisher jobEventPublisher;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;


    @Transactional
    public Job createJob(Job job) {
        job.setStatus(JobStatus.PENDING);
        Job savedJob =  jobRepository.save(job);

        try {

            OutboxEvent outboxEvent = new OutboxEvent(
                    "Job",
                    savedJob.getId().toString(),
                    "JOB_CREATED",
                    objectMapper.writeValueAsString(
                            new JobCreatedEvent(savedJob.getId(), savedJob.getType())
                    )
            );

            outboxEventRepository.save(outboxEvent);

        } catch (Exception e) {
            throw new IllegalStateException("Failed to serialize JobCreatedEvent", e);
        }

        return savedJob;

    }

    public Job getJob(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job not found: " + id));
    }

    public Page<Job> getJobsByStatus(JobStatus status, Pageable pageable) {
        return jobRepository.findByStatus(status, pageable);
    }


    @Transactional
    public void markFinished(Long id) {
        Job job = jobRepository.findById(id).orElseThrow();
        job.markFinished();
    }

    @Transactional
    public void markFailed(Long id) {
        Job job = jobRepository.findById(id).orElseThrow();
        job.markFailed("worker failed");
    }
}
