package com.karthik.taskplatform.jobservice.service;


import com.karthik.taskplatform.jobservice.domain.Job;
import com.karthik.taskplatform.jobservice.domain.JobStatus;
import com.karthik.taskplatform.jobservice.event.JobCreatedEvent;
import com.karthik.taskplatform.jobservice.event.JobEventPublisher;
import com.karthik.taskplatform.jobservice.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobService
{
    private final JobRepository jobRepository;
    private final JobEventPublisher jobEventPublisher;
    public Job createJob(Job job) {
        job.setStatus(JobStatus.PENDING);
        Job savedJob =  jobRepository.save(job);

        jobEventPublisher.publishJobCreated(
                new JobCreatedEvent(savedJob.getId(), savedJob.getType())
        );

        return savedJob;

    }

    public Job getJob(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job not found: " + id));
    }

    public Page<Job> getJobsByStatus(JobStatus status, Pageable pageable) {
        return jobRepository.findByStatus(status, pageable);
    }
}
