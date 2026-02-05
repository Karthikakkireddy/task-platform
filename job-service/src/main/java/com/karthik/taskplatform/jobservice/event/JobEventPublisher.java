package com.karthik.taskplatform.jobservice.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class JobEventPublisher {

    private static final String TOPIC = "job.created";

    private final KafkaTemplate<String, JobCreatedEvent> kafkaTemplate;

    public CompletableFuture<SendResult<String, JobCreatedEvent>> publishJobCreated(
            JobCreatedEvent event
    ) {
        return kafkaTemplate.send(
                TOPIC,
                event.getJobId().toString(),
                event
        );
    }
}