package com.example.demo.health;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HealthServiceTest {

    @Mock
    private MosipConfig mosipConfig;

    @InjectMocks
    private HealthService healthService;

    @Test
    void testGetHealthDetails_StatusIsUpOrDown() {

        when(mosipConfig.getSomeConfig()).thenReturn("test-config");

        HealthDetailsResponse healthDetailsResponse = healthService.getHealthDetails();
        assertNotNull(healthDetailsResponse);

        assertEquals("test-config", healthDetailsResponse.getSomeConfig());
        verify(mosipConfig, atLeastOnce()).getSomeConfig();


        assertNotNull(healthDetailsResponse.getMetadata());
        assertEquals("serviceName", healthDetailsResponse.getMetadata().getServiceName());
        assertEquals("qa", healthDetailsResponse.getMetadata().getEnvironment());
        assertEquals("3.10.4", healthDetailsResponse.getMetadata().getVersion());

        // Run multiple times to cover randomness
        for (int i = 0; i < 10; i++) {
            HealthDetailsResponse response = healthService.getHealthDetails();
            assertTrue(
                    response.getStatus().equals("UP") || response.getStatus().equals("DOWN"),
                    "Status should be either UP or DOWN"
            );
        }
    }

}

