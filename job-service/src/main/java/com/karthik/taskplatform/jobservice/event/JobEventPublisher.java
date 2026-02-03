package com.karthik.taskplatform.jobservice.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class JobEventPublisher
{
    private static final String TOPIC = "job.created";

    private final KafkaTemplate<String, JobCreatedEvent> kafkaTemplate;

    public void publishJobCreated(JobCreatedEvent event) {
        kafkaTemplate.send(TOPIC, event.getJobId().toString(), event);
    }
}
