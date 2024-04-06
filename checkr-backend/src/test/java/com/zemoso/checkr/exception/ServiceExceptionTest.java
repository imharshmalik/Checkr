package com.zemoso.checkr.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServiceExceptionTest {
    @Test
    void testConstructor() {
        String message = "Candidate not found";
        CandidateNotFoundException exception = new CandidateNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testServiceExceptionMessage() {
        String message = "Test message";
        ServiceException exception = new ServiceException(message);

        assertEquals(message, exception.getMessage());
    }
}