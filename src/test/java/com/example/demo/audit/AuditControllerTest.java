package com.example.demo.audit;

import com.example.demo.model.response.ResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuditController.class)
class AuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuditService auditService;

    @MockitoBean
    private ResponseModel responseModel;

    @Test
    void testAudit_Success() throws Exception {
        AuditResponse response = new AuditResponse();
        response.setEventId("123");
        response.setTimestamp("2025-09-30T10:15:30Z");

        when(auditService.audit(any(AuditRequest.class)))
                .thenReturn(response);

        AuditRequest request = new AuditRequest();
        request.setEventType("LOGIN");
        request.setUserId("user123");
        request.setDescription("User logged in");

        mockMvc.perform(post("/api/v1/audit/log")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value("123"))
                .andExpect(jsonPath("$.timestamp").value("2025-09-30T10:15:30Z"));
    }

    @Test
    @Disabled("Revisit error")
    void testAudit_ValidationFailure() throws Exception {
        // Missing userId → invalid
        AuditRequest invalidRequest = new AuditRequest();
        invalidRequest.setEventType("LOGIN");

        mockMvc.perform(post("/api/v1/audit/log")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}

