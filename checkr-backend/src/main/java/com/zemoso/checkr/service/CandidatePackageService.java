package com.zemoso.checkr.service;

import com.zemoso.checkr.dto.CandidatePackageDTO;
import com.zemoso.checkr.entities.CandidatePackage;
import com.zemoso.checkr.exception.ServiceException;
import com.zemoso.checkr.repository.CandidatePackageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CandidatePackageService {
    private final CandidatePackageRepository candidatePackageRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CandidatePackageService(CandidatePackageRepository candidatePackageRepository, ModelMapper modelMapper) {
        this.candidatePackageRepository = candidatePackageRepository;
        this.modelMapper = modelMapper;
    }

    public CandidatePackageDTO getCandidatePackageDTO(long id){
        Optional<CandidatePackage> candidatePackageOptional = candidatePackageRepository.findById(id);
        if (candidatePackageOptional.isPresent()) {
            CandidatePackage candidatePackage = candidatePackageOptional.get();
            return convertEntityToDTO(candidatePackage);
        } else {
            throw new ServiceException("An error occurred while fetching the Report details");
        }
    }

    public CandidatePackageDTO convertEntityToDTO(CandidatePackage candidatePackage){
        return modelMapper.map(candidatePackage, CandidatePackageDTO.class);
    }

}
