package com.karthik.taskplatform.jobservice.outbox;

import com.karthik.taskplatform.jobservice.event.JobCreatedEvent;
import com.karthik.taskplatform.jobservice.event.JobEventPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxPublisher {

    private final OutboxEventRepository outboxEventRepository;
    private final JobEventPublisher jobEventPublisher;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 3000)
    @Transactional
    public void publishPendingEvents() throws Exception {

        List<OutboxEvent> events =
                outboxEventRepository.findTop10ByPublishedFalseOrderByCreatedAtAsc();

        for (OutboxEvent event : events) {

            if ("JOB_CREATED".equals(event.getEventType())) {

                JobCreatedEvent jobCreatedEvent =
                        objectMapper.readValue(event.getPayload(), JobCreatedEvent.class);

                jobEventPublisher.publishJobCreated(jobCreatedEvent);

                event.markPublished();
            }
        }
    }
}