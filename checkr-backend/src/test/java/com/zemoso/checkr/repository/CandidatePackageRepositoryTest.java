package com.zemoso.checkr.repository;

import com.zemoso.checkr.entities.CandidatePackage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CandidatePackageRepositoryTest {
    @Mock
    private CandidatePackageRepository candidatePackageRepository;

    @Test
    void testFindById() {
        CandidatePackage candidatePackage = new CandidatePackage();
        candidatePackage.setId(1);
        candidatePackage.setName("Employee Pro");

        when(candidatePackageRepository.findById(1L)).thenReturn(Optional.of(candidatePackage));

        Optional<CandidatePackage> result = candidatePackageRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(candidatePackage, result.get());

        verify(candidatePackageRepository).findById(1L);
    }
}