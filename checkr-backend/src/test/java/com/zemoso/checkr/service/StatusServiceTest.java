package com.zemoso.checkr.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import com.zemoso.checkr.dto.StatusDTO;
import com.zemoso.checkr.entities.Status;
import com.zemoso.checkr.exception.ServiceException;
import com.zemoso.checkr.repository.StatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class StatusServiceTest {

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private StatusService statusService;

    @Test
    void getStatus() {
        long id = 1L;
        Status status = new Status();
        status.setId((int)id);
        status.setName("Cleared");

        when(statusRepository.findById(id)).thenReturn(Optional.of(status));

        StatusDTO expectedDTO = new StatusDTO((int) id, "Cleared");
        when(statusService.convertEntityToDTO(status)).thenReturn(expectedDTO);

        StatusDTO result = statusService.getStatus(id);

        assertNotNull(result);
        assertEquals(status.getId(), result.getId());
        assertEquals(status.getName(), result.getName());
    }

    @Test
    void testGetStatus_NotFound() {
        long id = 1L;

        when(statusRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> statusService.getStatus(id));

        verify(statusRepository, times(1)).findById(id);
    }

}