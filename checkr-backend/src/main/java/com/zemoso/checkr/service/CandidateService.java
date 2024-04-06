package com.zemoso.checkr.service;

import com.zemoso.checkr.dto.CandidateDTO;
import com.zemoso.checkr.entities.Candidate;
import com.zemoso.checkr.exception.CandidateNotFoundException;
import com.zemoso.checkr.exception.ServiceException;
import com.zemoso.checkr.repository.CandidateRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CandidateService(CandidateRepository candidateRepository, ModelMapper modelMapper) {
        this.candidateRepository = candidateRepository;
        this.modelMapper = modelMapper;
    }

    public CandidateDTO convertEntityToDTO(Candidate candidate){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(candidate, CandidateDTO.class);
    }

    public Candidate convertDTOToEntity(CandidateDTO candidateDTO){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(candidateDTO, Candidate.class);
    }

    public List<CandidateDTO> getAllCandidates(){
        try {
            return candidateRepository.findAll()
                    .stream()
                    .map(this::convertEntityToDTO)
                    .toList();
        } catch (Exception ex) {
            throw new ServiceException("An error occurred while fetching all candidates", ex);
        }
    }

    public CandidateDTO getCandidateById(Long id) {
        try {
            Candidate candidate = candidateRepository.findById(id)
                    .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with id: " + id));

            return convertEntityToDTO(candidate);
        } catch (Exception ex) {
            throw new ServiceException("An error occurred while fetching candidate by id: " + id, ex);
        }
    }

    public CandidateDTO createCandidate(CandidateDTO candidateDTO) {
        try {
            Candidate candidate = convertDTOToEntity(candidateDTO);
            Candidate savedCandidate = candidateRepository.save(candidate);
            return convertEntityToDTO(savedCandidate);
        } catch (Exception ex) {
            throw new ServiceException("An error occurred while creating candidate", ex);
        }
    }

    public List<CandidateDTO> getCandidatesByName(String name) {
        try {
            List<Candidate> candidates = candidateRepository.findByNameContainingIgnoreCase(name);
            return candidates.stream()
                    .map(candidate -> modelMapper.map(candidate, CandidateDTO.class))
                    .toList();
        } catch (Exception ex) {
            throw new ServiceException("An error occurred while fetching candidates by name", ex);
        }
    }

    public List<CandidateDTO> getCandidatesByStatusAndAdjudication(String status, String adjudication) {
        try {
            List<Candidate> candidates = candidateRepository.findByReport_Status_NameAndReport_Adjudication_Name(status, adjudication);
            return candidates.stream()
                    .map(candidate -> modelMapper.map(candidate, CandidateDTO.class))
                    .toList();
        } catch (Exception ex) {
            throw new ServiceException("Error occurred while fetching candidates by status and adjudication", ex);
        }
    }

    public List<CandidateDTO> getCandidatesByStatus(String status) {
        try {
            List<Candidate> candidates = candidateRepository.findByReport_Status_Name(status);
            return candidates.stream()
                    .map(candidate -> modelMapper.map(candidate, CandidateDTO.class))
                    .toList();
        } catch (Exception ex) {
            throw new ServiceException("Error occurred while fetching candidates by status", ex);
        }
    }

    public List<CandidateDTO> getCandidatesByAdjudication(String adjudication) {
        try {
            List<Candidate> candidates = candidateRepository.findByReport_Adjudication_Name(adjudication);
            return candidates.stream()
                    .map(candidate -> modelMapper.map(candidate, CandidateDTO.class))
                    .toList();
        } catch (Exception ex) {
            throw new ServiceException("Error occurred while fetching candidates by adjudication", ex);
        }
    }

    public List<CandidateDTO> getCandidatesBetweenDates(LocalDate startDate, LocalDate endDate) {
        try {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

            List<Candidate> candidates = candidateRepository.findByReportCreationDateBetween(startDateTime, endDateTime);

            return candidates.stream()
                    .map(this::convertEntityToDTO)
                    .toList();
        } catch (Exception ex) {
            throw new ServiceException("Error occurred while fetching candidates between dates", ex);
        }
    }

}
