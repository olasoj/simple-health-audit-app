package com.example.demo.audit;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AuditRepository {

    private static final AtomicInteger counter = new AtomicInteger(0);
    private final ConcurrentMap<Integer, Event> events = new ConcurrentHashMap<>();

    public Event recordEvent(Event event) {

        Assert.notNull(event, "Event must not be null");

        int id = counter.incrementAndGet();
        event.setEventId(id);

        events.put(id, event);
        return event;
    }
}
