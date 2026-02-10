package com.karthik.taskplatform.jobservice.controller;

import com.karthik.taskplatform.jobservice.domain.Job;
import com.karthik.taskplatform.jobservice.domain.JobStatus;
import com.karthik.taskplatform.jobservice.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/jobs")
@RequiredArgsConstructor
public class JobStatusController {

    private final JobRepository jobRepository;

    @PatchMapping("/{id}/status")
    public void updateStatus(@PathVariable Long id,
                             @RequestParam JobStatus status) {

        Job job = jobRepository.findById(id)
                .orElseThrow();

        job.setStatus(status);
        jobRepository.save(job);
    }
}
