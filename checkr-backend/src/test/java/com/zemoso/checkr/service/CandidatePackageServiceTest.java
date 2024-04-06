package com.zemoso.checkr.service;

import com.zemoso.checkr.dto.CandidatePackageDTO;
import com.zemoso.checkr.entities.CandidatePackage;
import com.zemoso.checkr.exception.ServiceException;
import com.zemoso.checkr.repository.CandidatePackageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CandidatePackageServiceTest {

    @Mock
    private CandidatePackageRepository candidatePackageRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CandidatePackageService candidatePackageService;
    @Test
    void getCandidatePackageDTO() {
        long id = 1L;
        CandidatePackage candidatePackage = new CandidatePackage();
        candidatePackage.setId((int) id);
        candidatePackage.setName("Employee pro");

        when(candidatePackageRepository.findById(id)).thenReturn(Optional.of(candidatePackage));

        CandidatePackageDTO expectedDTO = new CandidatePackageDTO((int) id, "Employee pro");
        when(candidatePackageService.convertEntityToDTO(candidatePackage)).thenReturn(expectedDTO);

        CandidatePackageDTO result = candidatePackageService.getCandidatePackageDTO(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Employee pro", result.getName());
    }

    @Test
    void testGetCandidatePackageDTO_NotFound() {
        long id = 1L;

        when(candidatePackageRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> candidatePackageService.getCandidatePackageDTO(id));

        verify(candidatePackageRepository, times(1)).findById(id);
    }

}