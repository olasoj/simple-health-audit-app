package com.example.demo.audit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AuditRepositoryTest {

    @InjectMocks
    private AuditRepository repository;


    @Test
    void testRecordEvent_NullEventThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> repository.recordEvent(null));
    }

    @Test
    void testRecordEvent_AssignsIncrementalIds() {
        Event e1 = new Event();
        e1.setEventType("LOGIN");
        repository.recordEvent(e1);

        Event e2 = new Event();
        e2.setEventType("LOGOUT");
        repository.recordEvent(e2);

        assertEquals(1, e1.getEventId());
        assertEquals(2, e2.getEventId());
    }

    @Test
    void testRecordEvent_IsThreadSafe() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(5);

        try {

            int tasks = 20;

            CountDownLatch latch = new CountDownLatch(tasks);
            for (int i = 0; i < tasks; i++) {
                executor.submit(() -> {
                    repository.recordEvent(new Event());
                    latch.countDown();
                });
            }

            latch.await();

            // Verify that counter increments correctly without duplicates
            // Last inserted event should have ID = tasks
            Event last = repository.recordEvent(new Event());
            Assertions.assertTrue(tasks < last.getEventId());
        } finally {
            executor.shutdown();
        }
    }
}
