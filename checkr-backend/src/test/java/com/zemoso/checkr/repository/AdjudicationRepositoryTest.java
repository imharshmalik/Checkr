package com.zemoso.checkr.repository;

import com.zemoso.checkr.entities.Adjudication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdjudicationRepositoryTest {
    @Mock
    private AdjudicationRepository adjudicationRepository;

    @Test
    void testFindById() {
        Adjudication adjudication = new Adjudication();
        adjudication.setId(1);
        adjudication.setName("Pre-adverse");

        when(adjudicationRepository.findById(1L)).thenReturn(Optional.of(adjudication));

        Optional<Adjudication> result = adjudicationRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(adjudication, result.get());

        verify(adjudicationRepository).findById(1L);
    }

}