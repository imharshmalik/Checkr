package com.zemoso.checkr.service;

import com.zemoso.checkr.dto.ReportDTO;
import com.zemoso.checkr.entities.Report;
import com.zemoso.checkr.exception.ServiceException;
import com.zemoso.checkr.repository.ReportRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final ModelMapper modelMapper;

    static {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }
    @Autowired
    public ReportService(ReportRepository reportRepository, ModelMapper modelMapper) {
        this.reportRepository = reportRepository;
        this.modelMapper = modelMapper;
    }

    public ReportDTO getReport(Long id){
        Optional<Report> reportOptional = reportRepository.findById(id);
        if (reportOptional.isPresent()) {
            Report report = reportOptional.get();
            return convertEntityToDTO(report);
        } else {
            throw new ServiceException("An error occurred while fetching the Report details");
        }
    }

    public ReportDTO convertEntityToDTO(Report report) {
        return modelMapper.map(report, ReportDTO.class);
    }

}
