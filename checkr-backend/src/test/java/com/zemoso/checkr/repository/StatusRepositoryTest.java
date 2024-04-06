package com.zemoso.checkr.repository;

import com.zemoso.checkr.entities.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatusRepositoryTest {

    @Mock
    private StatusRepository statusRepository;

    @Test
    void testFindById() {
        Status status = new Status();
        status.setId(1);
        status.setName("Active");

        when(statusRepository.findById(1L)).thenReturn(Optional.of(status));

        Optional<Status> result = statusRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(status, result.get());

        verify(statusRepository).findById(1L);
    }

}