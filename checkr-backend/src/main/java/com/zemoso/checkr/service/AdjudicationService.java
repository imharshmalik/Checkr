package com.zemoso.checkr.service;

import com.zemoso.checkr.dto.AdjudicationDTO;
import com.zemoso.checkr.entities.Adjudication;
import com.zemoso.checkr.exception.ServiceException;
import com.zemoso.checkr.repository.AdjudicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdjudicationService {
    private final AdjudicationRepository adjudicationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AdjudicationService(AdjudicationRepository adjudicationRepository, ModelMapper modelMapper) {
        this.adjudicationRepository = adjudicationRepository;
        this.modelMapper = modelMapper;
    }

    public AdjudicationDTO getAdjudication(long id){
        Optional<Adjudication> adjudicationOptional = adjudicationRepository.findById(id);
        if (adjudicationOptional.isPresent()) {
            Adjudication adjudication = adjudicationOptional.get();
            return convertEntityToDTO(adjudication);
        } else {
            throw new ServiceException("An error occurred while fetching the Report details");
        }
    }

    public AdjudicationDTO convertEntityToDTO(Adjudication adjudication){
        return modelMapper.map(adjudication, AdjudicationDTO.class);
    }
}
