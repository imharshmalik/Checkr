package com.zemoso.checkr.service;


import com.zemoso.checkr.dto.ReportDTO;
import com.zemoso.checkr.entities.Adjudication;
import com.zemoso.checkr.entities.CandidatePackage;
import com.zemoso.checkr.entities.Report;
import com.zemoso.checkr.entities.Status;
import com.zemoso.checkr.exception.ServiceException;
import com.zemoso.checkr.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    private ModelMapper modelMapper;
    @BeforeEach
    public void setup() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        reportService = new ReportService(reportRepository, modelMapper);
    }

    @Test
    void getReport() {
        long id = 1L;
        Report report = new Report();
        report.setId(id);
        report.setStatus(new Status("Cleared"));
        report.setAdjudication(new Adjudication("Pre-adverse"));
        report.setCandidatePackage(new CandidatePackage("Employee Pro"));
        when(reportRepository.findById(id)).thenReturn(Optional.of(report));

        ReportDTO result = reportService.getReport(id);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Cleared", result.getStatus().getName());
        assertEquals("Pre-adverse", result.getAdjudication().getName());
        assertEquals("Employee Pro", result.getCandidatePackage().getName());
    }

    @Test
    void testGetReport_NotFound() {
        long id = 1L;

        when(reportRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> reportService.getReport(id));

        verify(reportRepository, times(1)).findById(id);
    }
}