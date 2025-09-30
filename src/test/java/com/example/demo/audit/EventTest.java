package com.example.demo.audit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void testGettersAndSetters() {
        Event event = new Event();
        event.setEventId(1);
        event.setEventType("LOGIN");
        event.setUserId("user123");
        event.setDescription("User logged in successfully");

        assertEquals(1, event.getEventId());
        assertEquals("LOGIN", event.getEventType());
        assertEquals("user123", event.getUserId());
        assertEquals("User logged in successfully", event.getDescription());
    }

    @Test
    void testEqualsAndHashCode() {
        Event event1 = new Event();
        event1.setEventId(1);
        event1.setEventType("LOGIN");
        event1.setUserId("user123");
        event1.setDescription("User logged in successfully");

        Event event2 = new Event();
        event2.setEventId(1);
        event2.setEventType("LOGIN");
        event2.setUserId("user123");
        event2.setDescription("User logged in successfully");

        assertEquals(event1, event2);
        assertEquals(event1.hashCode(), event2.hashCode());
    }

    @Test
    void testNotEquals() {
        Event event1 = new Event();
        event1.setEventId(1);
        event1.setEventType("LOGIN");

        Event event2 = new Event();
        event2.setEventId(2);
        event2.setEventType("LOGOUT");

        assertNotEquals(event1, event2);
    }

    @Test
    void testToString() {
        Event event = new Event();
        event.setEventId(1);
        event.setEventType("LOGIN");
        event.setUserId("user123");
        event.setDescription("User logged in successfully");

        String toString = event.toString();
        assertTrue(toString.contains("eventId=1"));
        assertTrue(toString.contains("eventType=LOGIN"));
        assertTrue(toString.contains("userId=user123"));
        assertTrue(toString.contains("description=User logged in successfully"));
    }
}
