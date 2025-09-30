package com.example.demo.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AuditRequestTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testGettersAndSetters() {
        AuditRequest request = new AuditRequest();
        request.setEventType("LOGIN");
        request.setDescription("User login event");
        request.setUserId("user123");

        assertEquals("LOGIN", request.getEventType());
        assertEquals("User login event", request.getDescription());
        assertEquals("user123", request.getUserId());
    }

    @Test
    void testEqualsAndHashCode() {
        AuditRequest r1 = new AuditRequest();
        r1.setEventType("LOGIN");
        r1.setDescription("User login event");
        r1.setUserId("user123");

        AuditRequest r2 = new AuditRequest();
        r2.setEventType("LOGIN");
        r2.setDescription("User login event");
        r2.setUserId("user123");

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testNotEquals() {
        AuditRequest r1 = new AuditRequest();
        r1.setEventType("LOGIN");
        r1.setUserId("user123");

        AuditRequest r2 = new AuditRequest();
        r2.setEventType("LOGOUT");
        r2.setUserId("user456");

        assertNotEquals(r1, r2);
    }

    @Test
    void testToString() {
        AuditRequest request = new AuditRequest();
        request.setEventType("LOGIN");
        request.setDescription("User login event");
        request.setUserId("user123");

        String result = request.toString();
        assertTrue(result.contains("eventType=LOGIN"));
        assertTrue(result.contains("description=User login event"));
        assertTrue(result.contains("userId=user123"));
    }

    @Test
    void testAdditionalProperties() {
        AuditRequest request = new AuditRequest();
        request.setAdditionalProperty("ipAddress", "127.0.0.1");

        Map<String, Object> props = request.getAdditionalProperties();
        assertEquals("127.0.0.1", props.get("ipAddress"));
    }

    @Test
    void testValidationFailsWhenFieldsBlank() {
        AuditRequest request = new AuditRequest();
        request.setEventType(""); // blank
        request.setUserId("");    // blank

        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {

            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<AuditRequest>> violations = validator.validate(request);

            assertFalse(violations.isEmpty());
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Enter eventType")));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Enter userId")));
        }
    }

    @Test
    void testSerializationAndDeserialization() throws JsonProcessingException {
        AuditRequest request = new AuditRequest();
        request.setEventType("LOGIN");
        request.setDescription("User login event");
        request.setUserId("user123");
        request.setAdditionalProperty("ipAddress", "127.0.0.1");

        String json = mapper.writeValueAsString(request);

        assertTrue(json.contains("\"eventType\":\"LOGIN\""));
        assertTrue(json.contains("\"userId\":\"user123\""));
        assertTrue(json.contains("\"ipAddress\":\"127.0.0.1\""));

        AuditRequest deserialized = mapper.readValue(json, AuditRequest.class);
        assertEquals("LOGIN", deserialized.getEventType());
        assertEquals("user123", deserialized.getUserId());
        assertEquals("127.0.0.1", deserialized.getAdditionalProperties().get("ipAddress"));
    }
}
