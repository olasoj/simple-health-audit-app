package com.example.demo.health;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping(value = "/details")
    public ResponseEntity<HealthDetailsResponse> sendLink() {

        HealthDetailsResponse healthDetails = healthService.getHealthDetails();
        HttpStatus httpStatus = HealthStatus.DOWN.name().equals(healthDetails.getStatus()) ? HttpStatus.SERVICE_UNAVAILABLE : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(healthDetails);
    }
}
