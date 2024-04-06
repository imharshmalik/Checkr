package com.zemoso.checkr.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import com.zemoso.checkr.dto.CourtSearchDTO;
import com.zemoso.checkr.entities.CourtSearch;
import com.zemoso.checkr.exception.ServiceException;
import com.zemoso.checkr.repository.CourtSearchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class CourtSearchServiceTest {

    @Mock
    private CourtSearchRepository courtSearchRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CourtSearchService courtSearchService;

    @Test
    void testGetCourtSearch() {
        long id = 1L;
        CourtSearch courtSearch = new CourtSearch();
        courtSearch.setId((int) id);
        courtSearch.setName("Global Watchlist");

        when(courtSearchRepository.findById(id)).thenReturn(Optional.of(courtSearch));

        CourtSearchDTO expectedDTO = new CourtSearchDTO((int) id, "Global Watchlist");
        when(courtSearchService.convertEntityToDTO(courtSearch)).thenReturn(expectedDTO);

        CourtSearchDTO result = courtSearchService.getCourtSearch(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Global Watchlist", result.getName());
    }

    @Test
    void testGetCourtSearch_NotFound() {
        long id = 1L;

        when(courtSearchRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> courtSearchService.getCourtSearch(id));

        verify(courtSearchRepository, times(1)).findById(id);
    }
}