package com.example.demo.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MyService {

    private final RestTemplate restTemplate;

    public MyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "myServiceCircuitBreaker", fallbackMethod = "fallback")
    @Retry(name = "myServiceRetry")
    public String callExternalService() {
        return restTemplate.getForObject("http://external-service/api/resource", String.class);
    }

    public String fallback(Exception e) {
        return "Fallback response";
    }
}
