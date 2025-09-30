package com.example.demo.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AuditResponseTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testGettersAndSetters() {
        AuditResponse response = new AuditResponse();
        response.setTimestamp("2025-09-30T10:15:30Z");
        response.setEventId("EVT123");

        assertEquals("2025-09-30T10:15:30Z", response.getTimestamp());
        assertEquals("EVT123", response.getEventId());
    }

    @Test
    void testEqualsAndHashCode() {
        AuditResponse r1 = new AuditResponse();
        r1.setTimestamp("2025-09-30T10:15:30Z");
        r1.setEventId("EVT123");

        AuditResponse r2 = new AuditResponse();
        r2.setTimestamp("2025-09-30T10:15:30Z");
        r2.setEventId("EVT123");

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testNotEquals() {
        AuditResponse r1 = new AuditResponse();
        r1.setTimestamp("2025-09-30T10:15:30Z");
        r1.setEventId("EVT123");

        AuditResponse r2 = new AuditResponse();
        r2.setTimestamp("2025-09-30T10:20:00Z");
        r2.setEventId("EVT456");

        assertNotEquals(r1, r2);
    }

    @Test
    void testToString() {
        AuditResponse response = new AuditResponse();
        response.setTimestamp("2025-09-30T10:15:30Z");
        response.setEventId("EVT123");

        String result = response.toString();
        assertTrue(result.contains("timestamp=2025-09-30T10:15:30Z"));
        assertTrue(result.contains("eventId=EVT123"));
    }

    @Test
    void testAdditionalProperties() {
        AuditResponse response = new AuditResponse();
        response.setAdditionalProperty("status", "SUCCESS");

        Map<String, Object> props = response.getAdditionalProperties();
        assertEquals("SUCCESS", props.get("status"));
    }

    @Test
    void testSerializationAndDeserialization() throws JsonProcessingException {
        AuditResponse response = new AuditResponse();
        response.setTimestamp("2025-09-30T10:15:30Z");
        response.setEventId("EVT123");
        response.setAdditionalProperty("status", "SUCCESS");

        // Serialize
        String json = mapper.writeValueAsString(response);
        assertTrue(json.contains("\"timestamp\":\"2025-09-30T10:15:30Z\""));
        assertTrue(json.contains("\"eventId\":\"EVT123\""));
        assertTrue(json.contains("\"status\":\"SUCCESS\""));

        // Deserialize
        AuditResponse deserialized = mapper.readValue(json, AuditResponse.class);
        assertEquals("2025-09-30T10:15:30Z", deserialized.getTimestamp());
        assertEquals("EVT123", deserialized.getEventId());
        assertEquals("SUCCESS", deserialized.getAdditionalProperties().get("status"));
    }
}

