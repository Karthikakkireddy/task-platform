package com.karthik.taskplatform.workerservice.consumer;

import com.karthik.taskplatform.workerservice.client.JobServiceClient;
import com.karthik.taskplatform.workerservice.event.JobCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobCreatedConsumer {

    private final JobServiceClient jobServiceClient;

    @KafkaListener(
            topics = "job.created",
            groupId = "worker-service"
    )
    public void onJobCreated(JobCreatedEvent event) {

        jobServiceClient.updateStatus(event.getJobId(), "RUNNING");

        System.out.println("Processing job: " + event.getJobId());

        try {
            Thread.sleep(2000);

            jobServiceClient.updateStatus(event.getJobId(), "FINISHED");
            System.out.println("Finished job: " + event.getJobId());

        } catch (Exception e) {

            jobServiceClient.updateStatus(event.getJobId(), "FAILED");
            System.out.println("Failed job: " + event.getJobId());
        }
    }
}