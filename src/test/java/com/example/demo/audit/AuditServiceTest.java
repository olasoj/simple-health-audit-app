package com.example.demo.audit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private AuditService auditService;

    @Test
    void testAudit_Success() {
        // Arrange
        AuditRequest request = new AuditRequest();
        request.setEventType("LOGIN");
        request.setUserId("user123");
        request.setDescription("User logged in");

        Event savedEvent = new Event();
        savedEvent.setEventId(1001);
        when(auditRepository.recordEvent(any(Event.class))).thenReturn(savedEvent);

        // Act
        AuditResponse response = auditService.audit(request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getTimestamp()); // generated at runtime
        assertEquals("1001", response.getEventId());

        // Verify correct Event was passed to repository
        ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
        verify(auditRepository).recordEvent(captor.capture());
        Event captured = captor.getValue();
        assertEquals("LOGIN", captured.getEventType());
        assertEquals("user123", captured.getUserId());
        assertEquals("User logged in", captured.getDescription());
    }

    @Test
    void testAudit_NullRequestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> auditService.audit(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void testAudit_BlankEventTypeThrowsException(String blankInput) {
        AuditRequest request = new AuditRequest();
        request.setEventType(blankInput);
        request.setUserId("user123");

        assertThrows(IllegalArgumentException.class, () -> auditService.audit(request));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void testAudit_BlankUserIdThrowsException(String blankInput) {
        AuditRequest request = new AuditRequest();
        request.setEventType("LOGIN");
        request.setUserId(blankInput);

        assertThrows(IllegalArgumentException.class, () -> auditService.audit(request));
    }
}
