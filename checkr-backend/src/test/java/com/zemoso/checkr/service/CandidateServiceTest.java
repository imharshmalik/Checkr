package com.zemoso.checkr.service;

import com.zemoso.checkr.dto.AdjudicationDTO;
import com.zemoso.checkr.dto.CandidateDTO;
import com.zemoso.checkr.dto.ReportDTO;
import com.zemoso.checkr.dto.StatusDTO;
import com.zemoso.checkr.entities.Adjudication;
import com.zemoso.checkr.entities.Candidate;
import com.zemoso.checkr.entities.Report;
import com.zemoso.checkr.entities.Status;
import com.zemoso.checkr.exception.ServiceException;
import com.zemoso.checkr.repository.CandidateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CandidateServiceTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CandidateService candidateService;

    @BeforeEach
    void setUp() {
        try (MockedStatic<MockitoAnnotations> mockedStatic = Mockito.mockStatic(MockitoAnnotations.class)) {
            mockedStatic.when(() -> MockitoAnnotations.openMocks(this)).thenReturn(null);
            MockitoAnnotations.openMocks(this);
        }

        when(modelMapper.getConfiguration()).thenReturn(new ModelMapper().getConfiguration());

        Configuration configuration = modelMapper.getConfiguration();
        configuration.setMatchingStrategy(MatchingStrategies.STRICT);

        Candidate candidate = new Candidate.CandidateBuilder()
                .setId(1)
                .setName("James Cameron")
                .setLocation("London")
                .setDob(LocalDate.of(1990, 1, 1))
                .setEmail("test@example.com")
                .setPhone("1234567890")
                .setZipcode("12345")
                .setSocialSecurity("123-45-6789")
                .setCreationDate(LocalDateTime.now())
                .setDriversLicense("ABC123")
                .setReport(new Report())
                .build();
        CandidateDTO candidateDTO = new CandidateDTO.CandidateBuilder()
                .setId(1)
                .setName("James Cameron")
                .setLocation("London")
                .setDob(LocalDate.of(1990, 1, 1))
                .setEmail("test@example.com")
                .setPhone("1234567890")
                .setZipcode("12345")
                .setSocialSecurity("123-45-6789")
                .setCreationDate(LocalDateTime.now())
                .setDriversLicense("ABC123")
                .setReport(new ReportDTO())
                .build();
        when(candidateRepository.findById(1L)).thenReturn(Optional.ofNullable(candidate));
    }

    @Test
    void testGetAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        candidates.add(new Candidate("Alice"));
        candidates.add(new Candidate("Bob"));
        when(candidateRepository.findAll()).thenReturn(candidates);

        List<CandidateDTO> result = candidateService.getAllCandidates();

        assertEquals(2, result.size());

        verify(candidateRepository).findAll();
    }

    @Test
    void testGetAllCandidates_Exception() {
        when(candidateRepository.findAll()).thenThrow(new RuntimeException("Database connection error"));

        assertThrows(ServiceException.class, () -> candidateService.getAllCandidates());
    }

    @Test
    void getCandidateById_Success() {
        Long candidateId = 1L;
        Candidate candidate = new Candidate();
        candidate.setId(Math.toIntExact(candidateId));
        candidate.setName("John Doe");

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setId(Math.toIntExact(candidateId));
        candidateDTO.setName("John Doe");

        when(modelMapper.map(candidate, CandidateDTO.class)).thenReturn(candidateDTO);

        CandidateDTO result = candidateService.getCandidateById(candidateId);

        assertEquals(candidateId, result.getId());
        assertEquals("John Doe", result.getName());

        verify(candidateRepository).findById(candidateId);
        verify(modelMapper).map(candidate, CandidateDTO.class);
    }

    @Test
    void testGetCandidateById_CandidateNotFoundException() {
        Long id = 1L;
        when(candidateRepository.findById(id)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> candidateService.getCandidateById(id));
        assertEquals("An error occurred while fetching candidate by id: 1", exception.getMessage());
    }

    @Test
    void testGetCandidateById_ServiceException() {
        Long id = 1L;
        when(candidateRepository.findById(id)).thenThrow(new RuntimeException("Database error"));

        assertThrows(ServiceException.class, () -> candidateService.getCandidateById(id));
    }

    @Test
    void createCandidate() {
        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setId(1);
        candidateDTO.setName("John Doe");

        Candidate candidate = new Candidate();
        candidate.setId(1);
        candidate.setName("John Doe");

        when(modelMapper.map(candidateDTO, Candidate.class)).thenReturn(candidate);

        candidateService.createCandidate(candidateDTO);

        verify(modelMapper).map(candidateDTO, Candidate.class);

        verify(candidateRepository).save(candidate);
    }

    @Test
    void testCreateCandidate_ServiceException() {
        CandidateDTO candidateDTO = new CandidateDTO();
        doThrow(new RuntimeException("Error")).when(candidateRepository).save(any());

        assertThrows(ServiceException.class, () -> candidateService.createCandidate(candidateDTO));

        verify(candidateRepository, times(1)).save(any());
    }

    @Test
    void getCandidatesByName() {
        String name = "James Cameron";
        Candidate candidate1 = new Candidate();
        candidate1.setId(1);
        candidate1.setName(name);

        Candidate candidate2 = new Candidate();
        candidate2.setId(2);
        candidate2.setName(name);

        List<Candidate> candidates = Arrays.asList(candidate1, candidate2);

        when(candidateRepository.findByNameContainingIgnoreCase(name)).thenReturn(candidates);

        CandidateDTO candidateDTO1 = new CandidateDTO();
        candidateDTO1.setId(1);
        candidateDTO1.setName(name);

        CandidateDTO candidateDTO2 = new CandidateDTO();
        candidateDTO2.setId(2);
        candidateDTO2.setName(name);

        when(modelMapper.map(candidate1, CandidateDTO.class)).thenReturn(candidateDTO1);
        when(modelMapper.map(candidate2, CandidateDTO.class)).thenReturn(candidateDTO2);

        List<CandidateDTO> results = candidateService.getCandidatesByName(name);

        assertEquals(2, results.size());
        assertEquals(1L, results.get(0).getId());
        assertEquals("James Cameron", results.get(0).getName());
        assertEquals(2L, results.get(1).getId());
        assertEquals("James Cameron", results.get(1).getName());

        verify(candidateRepository).findByNameContainingIgnoreCase(name);
        verify(modelMapper).map(candidate1, CandidateDTO.class);
        verify(modelMapper).map(candidate2, CandidateDTO.class);

    }

    @Test
    void testGetCandidatesByName_ServiceException() {
        String name = "John Doe";
        when(candidateRepository.findByNameContainingIgnoreCase(name)).thenThrow(new RuntimeException("Error"));

        assertThrows(ServiceException.class, () -> candidateService.getCandidatesByName(name));

        verify(candidateRepository, times(1)).findByNameContainingIgnoreCase(name);
    }

    @Test
    void getCandidatesByStatusAndAdjudication() {
        String status = "Cleared";
        String adjudication = "Pre-adverse";

        Candidate candidate1 = new Candidate();
        candidate1.setId(1);
        Report report1 = new Report();
        report1.setAdjudication(new Adjudication(adjudication));
        report1.setStatus(new Status(status));
        candidate1.setReport(report1);

        Candidate candidate2 = new Candidate();
        candidate2.setId(2);
        Report report2 = new Report();
        report2.setAdjudication(new Adjudication(adjudication));
        report2.setStatus(new Status(status));
        candidate2.setReport(report2);


        List<Candidate> candidates = Arrays.asList(candidate1, candidate2);

        when(candidateRepository.findByReport_Status_NameAndReport_Adjudication_Name(status, adjudication)).thenReturn(candidates);

        CandidateDTO candidateDTO1 = new CandidateDTO();
        candidateDTO1.setId(1);
        ReportDTO reportDTO1 = new ReportDTO();
        reportDTO1.setAdjudication(new AdjudicationDTO(adjudication));
        reportDTO1.setStatus(new StatusDTO(status));
        candidateDTO1.setReport(reportDTO1);

        CandidateDTO candidateDTO2 = new CandidateDTO();
        candidateDTO2.setId(2);
        ReportDTO reportDTO2 = new ReportDTO();
        reportDTO2.setAdjudication(new AdjudicationDTO(adjudication));
        reportDTO2.setStatus(new StatusDTO(status));
        candidateDTO2.setReport(reportDTO2);

        when(modelMapper.map(candidate1, CandidateDTO.class)).thenReturn(candidateDTO1);
        when(modelMapper.map(candidate2, CandidateDTO.class)).thenReturn(candidateDTO2);

        List<CandidateDTO> results = candidateService.getCandidatesByStatusAndAdjudication(status, adjudication);

        assertEquals(2, results.size());
        assertEquals(1L, results.get(0).getId());
        assertEquals("Cleared", results.get(0).getReport().getStatus().getName());
        assertEquals("Pre-adverse", results.get(0).getReport().getAdjudication().getName());
        assertEquals(2L, results.get(1).getId());
        assertEquals("Cleared", results.get(1).getReport().getStatus().getName());
        assertEquals("Pre-adverse", results.get(1).getReport().getAdjudication().getName());

        verify(candidateRepository).findByReport_Status_NameAndReport_Adjudication_Name(status, adjudication);
        verify(modelMapper).map(candidate1, CandidateDTO.class);
        verify(modelMapper).map(candidate2, CandidateDTO.class);

    }

    @Test
    void testGetCandidatesByStatusAndAdjudication_ServiceException() {
        String status = "status";
        String adjudication = "adjudication";
        when(candidateRepository.findByReport_Status_NameAndReport_Adjudication_Name(status, adjudication))
                .thenThrow(new RuntimeException("Error"));

        assertThrows(ServiceException.class, () -> candidateService.getCandidatesByStatusAndAdjudication(status, adjudication));

        verify(candidateRepository, times(1)).findByReport_Status_NameAndReport_Adjudication_Name(status, adjudication);
    }

    @Test
    void getCandidatesByStatus() {
        Status status = new Status();
        status.setId(1);
        status.setName("Cleared");

        Candidate candidate1 = new Candidate();
        candidate1.setId(1);
        candidate1.setName("James Cameron");

        Candidate candidate2 = new Candidate();
        candidate2.setId(2);
        candidate2.setName("James Smith");

        List<Candidate> candidates = Arrays.asList(candidate1, candidate2);

        when(candidateRepository.findByReport_Status_Name(status.getName())).thenReturn(candidates);

        CandidateDTO candidateDTO1 = new CandidateDTO();
        candidateDTO1.setId(1);
        candidateDTO1.setName("James Cameron");

        CandidateDTO candidateDTO2 = new CandidateDTO();
        candidateDTO2.setId(2);
        candidateDTO2.setName("James Smith");

        when(modelMapper.map(candidate1, CandidateDTO.class)).thenReturn(candidateDTO1);
        when(modelMapper.map(candidate2, CandidateDTO.class)).thenReturn(candidateDTO2);

        List<CandidateDTO> result = candidateService.getCandidatesByStatus(status.getName());

        assertEquals(2, result.size());
        assertEquals("James Cameron", result.get(0).getName());
        assertEquals("James Smith", result.get(1).getName());

        verify(candidateRepository).findByReport_Status_Name(status.getName());
        verify(modelMapper).map(candidate1, CandidateDTO.class);
        verify(modelMapper).map(candidate2, CandidateDTO.class);
    }

    @Test
    void testGetCandidatesByStatus_ServiceException() {
        String status = "status";
        when(candidateRepository.findByReport_Status_Name(status))
                .thenThrow(new RuntimeException("Error"));

        assertThrows(ServiceException.class, () -> candidateService.getCandidatesByStatus(status));

        verify(candidateRepository, times(1)).findByReport_Status_Name(status);
    }

    @Test
    void getCandidatesByAdjudication() {
        String adjudication = "Pre-adverse";
        Candidate candidate1 = new Candidate();
        candidate1.setId(1);
        candidate1.setName("James Cameron");

        Candidate candidate2 = new Candidate();
        candidate2.setId(2);
        candidate2.setName("James Smith");

        List<Candidate> candidates = Arrays.asList(candidate1, candidate2);

        when(candidateRepository.findByReport_Adjudication_Name(adjudication)).thenReturn(candidates);

        CandidateDTO candidateDTO1 = new CandidateDTO();
        candidateDTO1.setId(1);
        candidateDTO1.setName("James Cameron");

        CandidateDTO candidateDTO2 = new CandidateDTO();
        candidateDTO2.setId(2);
        candidateDTO2.setName("James Smith");


        when(modelMapper.map(candidate1, CandidateDTO.class)).thenReturn(candidateDTO1);
        when(modelMapper.map(candidate2, CandidateDTO.class)).thenReturn(candidateDTO2);

        List<CandidateDTO> results = candidateService.getCandidatesByAdjudication(adjudication);

        assertEquals(2, results.size());
        assertEquals(1L, results.get(0).getId());
        assertEquals("James Cameron", results.get(0).getName());
        assertEquals(2L, results.get(1).getId());
        assertEquals("James Smith", results.get(1).getName());

        verify(candidateRepository).findByReport_Adjudication_Name(adjudication);
        verify(modelMapper).map(candidate1, CandidateDTO.class);
        verify(modelMapper).map(candidate2, CandidateDTO.class);
    }

    @Test
    void testGetCandidatesByAdjudication_ServiceException() {
        String adjudication = "adjudication";
        when(candidateRepository.findByReport_Adjudication_Name(adjudication))
                .thenThrow(new RuntimeException("Error"));

        assertThrows(ServiceException.class, () -> candidateService.getCandidatesByAdjudication(adjudication));

        verify(candidateRepository, times(1)).findByReport_Adjudication_Name(adjudication);
    }

    @Test
    void testGetCandidatesBetweenDates_Success() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 31);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<Candidate> candidates = new ArrayList<>();
        candidates.add(new Candidate());
        candidates.add(new Candidate());

        when(candidateRepository.findByReportCreationDateBetween(startDateTime, endDateTime)).thenReturn(candidates);

        List<CandidateDTO> result = candidateService.getCandidatesBetweenDates(startDate, endDate);

        assertEquals(2, result.size());
        verify(candidateRepository, times(1)).findByReportCreationDateBetween(startDateTime, endDateTime);
    }

    @Test
    void testGetCandidatesBetweenDates_ServiceException() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 31);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        when(candidateRepository.findByReportCreationDateBetween(startDateTime, endDateTime))
                .thenThrow(new RuntimeException("Error"));

        assertThrows(ServiceException.class, () -> candidateService.getCandidatesBetweenDates(startDate, endDate));

        verify(candidateRepository, times(1)).findByReportCreationDateBetween(startDateTime, endDateTime);
    }
}