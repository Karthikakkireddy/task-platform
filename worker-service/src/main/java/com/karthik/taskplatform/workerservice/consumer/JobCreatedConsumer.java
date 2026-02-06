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
    public void onJobCreated(JobCreatedEvent event) throws InterruptedException {

        System.out.println("Processing job: " + event.getJobId());

        Thread.sleep(2000);

        System.out.println("Finished job: " + event.getJobId());
    }
}