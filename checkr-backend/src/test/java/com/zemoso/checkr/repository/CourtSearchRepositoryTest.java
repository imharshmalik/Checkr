package com.zemoso.checkr.repository;

import com.zemoso.checkr.entities.CourtSearch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourtSearchRepositoryTest {
    @Mock
    private CourtSearchRepository courtSearchRepository;

    @Test
    void testFindById() {
        CourtSearch courtSearch = new CourtSearch();
        courtSearch.setId(1);
        courtSearch.setName("Global Watchlist");

        when(courtSearchRepository.findById(1L)).thenReturn(Optional.of(courtSearch));

        Optional<CourtSearch> result = courtSearchRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(courtSearch, result.get());

        verify(courtSearchRepository).findById(1L);
    }
}