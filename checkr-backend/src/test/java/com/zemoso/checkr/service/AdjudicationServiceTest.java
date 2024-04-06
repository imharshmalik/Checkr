package com.zemoso.checkr.service;

import com.zemoso.checkr.dto.AdjudicationDTO;
import com.zemoso.checkr.entities.Adjudication;
import com.zemoso.checkr.exception.ServiceException;
import com.zemoso.checkr.repository.AdjudicationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdjudicationServiceTest {

    @Mock
    private AdjudicationRepository adjudicationRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AdjudicationService adjudicationService;

    @Test
    void getAdjudication() {
        long id = 1L;
        Adjudication adjudication = new Adjudication();
        adjudication.setId((int) id);
        adjudication.setName("Pre-adverse");

        when(adjudicationRepository.findById(id)).thenReturn(Optional.of(adjudication));
        when(adjudicationService.convertEntityToDTO(adjudication)).thenReturn((new AdjudicationDTO((int) id, "Pre-adverse")));

        AdjudicationDTO result = adjudicationService.getAdjudication(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Pre-adverse", result.getName());
    }

    @Test
    void testGetAdjudication_NotFound() {
        long id = 1L;

        when(adjudicationRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> adjudicationService.getAdjudication(id));

        verify(adjudicationRepository, times(1)).findById(id);
    }
}