package com.karthik.taskplatform.workerservice.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JobCreatedEvent {

    private Long jobId;
    private String jobType;
}