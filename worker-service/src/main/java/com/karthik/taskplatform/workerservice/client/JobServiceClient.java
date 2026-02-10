package com.karthik.taskplatform.workerservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class JobServiceClient {

    private final WebClient webClient;

    public JobServiceClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://localhost:8080")
                .build();
    }

    public void updateStatus(Long jobId, String status) {
        webClient.patch()
                .uri("/internal/jobs/{id}/status?status={status}", jobId, status)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}