package com.karthik.taskplatform.jobservice.controller;


import com.karthik.taskplatform.jobservice.domain.Job;
import com.karthik.taskplatform.jobservice.domain.JobStatus;
import com.karthik.taskplatform.jobservice.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController
{
    private final JobService jobService;

    @PostMapping
    public Job createJob(@RequestBody Job job) {
        return jobService.createJob(job);
    }

    @GetMapping("/{id}")
    public Job getJob(@PathVariable Long id) {
        return jobService.getJob(id);
    }

    @GetMapping
    public Page<Job> getJobsByStatus(
            @RequestParam JobStatus status,
            Pageable pageable
    ) {
        return jobService.getJobsByStatus(status, pageable);
    }

}
