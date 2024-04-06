package com.zemoso.checkr.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CandidateNotFoundExceptionTest {
    @Test
    void testConstructor() {
        String message = "Candidate not found";
        CandidateNotFoundException exception = new CandidateNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }
}