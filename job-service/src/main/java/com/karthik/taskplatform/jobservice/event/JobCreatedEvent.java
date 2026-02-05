package com.karthik.taskplatform.jobservice.event;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JobCreatedEvent
{
    private Long jobId;
    private String jobType;
}
