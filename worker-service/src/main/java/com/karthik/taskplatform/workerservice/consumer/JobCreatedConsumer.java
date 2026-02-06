package com.karthik.taskplatform.workerservice.consumer;

import com.karthik.taskplatform.workerservice.event.JobCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class JobCreatedConsumer {

    @KafkaListener(
            topics = "job.created",
            groupId = "worker-service"
    )
    public void onJobCreated(JobCreatedEvent event) {

        System.out.println("Received job event: " + event.getJobId()
                + " type=" + event.getJobType());
    }
}