package com.example.demo.health;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
public class HealthService {

    private final MosipConfig mosipConfig;

    private static final Random randomNumberGenerator = new Random();

    public HealthService(MosipConfig mosipConfig) {
        this.mosipConfig = mosipConfig;
    }

    public HealthDetailsResponse getHealthDetails() {

        HealthDetailsResponse healthDetailsResponse = new HealthDetailsResponse();

        int random = randomNumberGenerator.nextInt();
        String status = (random % 2 == 0) ? HealthStatus.UP.name() : HealthStatus.DOWN.name();
        healthDetailsResponse.setStatus(status);

        healthDetailsResponse.setTimestamp(Instant.now().toString());
        healthDetailsResponse.setSomeConfig(mosipConfig.getSomeConfig());

        HealthDetailsResponse.Metadata metadata = new HealthDetailsResponse.Metadata();

        metadata.setServiceName("serviceName");
        metadata.setEnvironment("qa");
        metadata.setVersion("3.10.4");

        healthDetailsResponse.setMetadata(metadata);

        return healthDetailsResponse;
    }
}
