package com.zemoso.checkr.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private UserNotFoundException userNotFoundException;

    @Mock
    private CandidateNotFoundException candidateNotFoundException;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void testHandleException() {
        Exception exception = new Exception("Test exception");

        ResponseEntity<String> response = globalExceptionHandler.handleException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        assertEquals("An unexpected error occurred: Test exception", response.getBody());
    }
    @Test
    void testHandleUserNotFoundException() {
        when(userNotFoundException.getMessage()).thenReturn("User not found");
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleUserNotFoundException(userNotFoundException);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("User not found: User not found", responseEntity.getBody());
    }

    @Test
    void testHandleCandidateNotFoundException() {
        when(candidateNotFoundException.getMessage()).thenReturn("Candidate not found");
        ResponseEntity<String> responseEntity = globalExceptionHandler.handleCandidateNotFoundException(candidateNotFoundException);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Candidate not found: Candidate not found", responseEntity.getBody());
    }

}