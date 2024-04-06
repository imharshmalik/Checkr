package com.zemoso.checkr.service;

import com.zemoso.checkr.dto.CourtSearchDTO;
import com.zemoso.checkr.entities.CourtSearch;
import com.zemoso.checkr.exception.ServiceException;
import com.zemoso.checkr.repository.CourtSearchRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourtSearchService {
    private final CourtSearchRepository courtSearchRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CourtSearchService(CourtSearchRepository courtSearchRepository, ModelMapper modelMapper) {
        this.courtSearchRepository = courtSearchRepository;
        this.modelMapper = modelMapper;
    }

    public CourtSearchDTO getCourtSearch(Long id){
        Optional<CourtSearch> courtSearchOptional = courtSearchRepository.findById(id);
        if (courtSearchOptional.isPresent()) {
            CourtSearch courtSearch = courtSearchOptional.get();
            return convertEntityToDTO(courtSearch);
        } else {
            throw new ServiceException("An error occurred while fetching the Report details");
        }
    }

    public CourtSearchDTO convertEntityToDTO(CourtSearch courtSearch){
        return modelMapper.map(courtSearch, CourtSearchDTO.class);
    }

}
