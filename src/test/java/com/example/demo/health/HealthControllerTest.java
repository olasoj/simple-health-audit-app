package com.example.demo.health;

import com.example.demo.model.response.ResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HealthController.class)
class HealthControllerTest {

    private final String uriTemplate = "/api/v1/health/details";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private HealthService healthService;

    @MockitoBean
    private ResponseModel responseModel;

    private HealthDetailsResponse createResponse(String status) {
        HealthDetailsResponse response = new HealthDetailsResponse();
        response.setStatus(status);
        response.setTimestamp("123456789");
        response.setSomeConfig("test-config");

        HealthDetailsResponse.Metadata metadata = new HealthDetailsResponse.Metadata();
        metadata.setServiceName("serviceName");
        metadata.setEnvironment("qa");
        metadata.setVersion("3.10.4");
        response.setMetadata(metadata);

        return response;
    }

    @Test
    void testSendLink_StatusUp_ReturnsOk() throws Exception {
        HealthDetailsResponse response = createResponse("UP");
        when(healthService.getHealthDetails()).thenReturn(response);

        mockMvc.perform(get(uriTemplate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.timestamp").value("123456789"))
                .andExpect(jsonPath("$.someConfig").value("test-config"));
    }

    @Test
    void testSendLink_StatusDown_ReturnsServiceUnavailable() throws Exception {
        HealthDetailsResponse response = createResponse("DOWN");
        when(healthService.getHealthDetails()).thenReturn(response);

        mockMvc.perform(get(uriTemplate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.status").value("DOWN"));
    }

    @Test
    void testSendLink_ResponseNotNull() throws Exception {
        when(healthService.getHealthDetails()).thenReturn(createResponse("UP"));

        mockMvc.perform(get(uriTemplate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
