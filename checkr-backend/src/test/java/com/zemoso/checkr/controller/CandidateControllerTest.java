package com.zemoso.checkr.controller;

import com.zemoso.checkr.dto.*;
import com.zemoso.checkr.service.CandidateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CandidateControllerTest{

    @Mock
    private CandidateService candidateService;

    @InjectMocks
    private CandidateController candidateController;

    @Test
     void testGetCandidateById() {
        int candidateId = 1;
        CandidateDTO expectedCandidate = new CandidateDTO();
        expectedCandidate.setId(candidateId);
        when(candidateService.getCandidateById((long) candidateId)).thenReturn(expectedCandidate);

        ResponseEntity<CandidateDTO> response = candidateController.getCandidateById((long) candidateId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCandidate, response.getBody());
    }

    @Test
     void testGetAllCandidates() {
        List<CandidateDTO> expectedCandidates = new ArrayList<>();
        when(candidateService.getAllCandidates()).thenReturn(expectedCandidates);

        ResponseEntity<List<CandidateDTO>> response = candidateController.getAllCandidates();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCandidates, response.getBody());
    }

    @Test
    void testGetCandidatesByNameWithCandidatesFound() {
        String name = "Virat Kohli";
        List<CandidateDTO> expectedCandidates = new ArrayList<>();
        expectedCandidates.add(new CandidateDTO());

        when(candidateService.getCandidatesByName(name)).thenReturn(expectedCandidates);

        ResponseEntity<List<CandidateDTO>> response = candidateController.getCandidatesByName(name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCandidates, response.getBody());

        verify(candidateService, times(1)).getCandidatesByName(name);
    }

    @Test
    void testGetCandidatesByNameWithNoCandidatesFound() {
        String name = "James Cameron";

        when(candidateService.getCandidatesByName(name)).thenReturn(Collections.emptyList());

        ResponseEntity<List<CandidateDTO>> response = candidateController.getCandidatesByName(name);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(candidateService, times(1)).getCandidatesByName(name);
    }

    @Test
    void testFilterCandidatesByStatusAndAdjudication_BothFilters() {
        CandidateDTO candidateDTO1 = new CandidateDTO();
        candidateDTO1.setName("Steve");

        CandidateDTO candidateDTO2 = new CandidateDTO();
        candidateDTO2.setName("Bill");

        List<CandidateDTO> expectedCandidates = Arrays.asList(candidateDTO1, candidateDTO2);

        when(candidateService.getCandidatesByStatusAndAdjudication("Cleared", "Pre-adverse")).thenReturn(expectedCandidates);

        ResponseEntity<List<CandidateDTO>> response = candidateController.filterCandidatesByStatusAndAdjudication("Cleared", "Pre-adverse");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCandidates, response.getBody());
    }

    @Test
    void testFilterCandidatesByStatusAndAdjudication_OnlyStatus() {
        String status = "Cleared";
        List<CandidateDTO> expectedCandidates = new ArrayList<>();

        when(candidateService.getCandidatesByStatus(status)).thenReturn(expectedCandidates);

        ResponseEntity<List<CandidateDTO>> response = candidateController.filterCandidatesByStatusAndAdjudication(status, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCandidates, response.getBody());

        verify(candidateService, times(1)).getCandidatesByStatus(status);
    }

    @Test
    void testFilterCandidatesByStatusAndAdjudication_OnlyAdjudication() {
        String adjudication = "Pre-adverse";
        List<CandidateDTO> expectedCandidates = new ArrayList<>();

        when(candidateService.getCandidatesByAdjudication(adjudication)).thenReturn(expectedCandidates);

        ResponseEntity<List<CandidateDTO>> response = candidateController.filterCandidatesByStatusAndAdjudication(null, adjudication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCandidates, response.getBody());

        verify(candidateService, times(1)).getCandidatesByAdjudication(adjudication);
    }

    @Test
    void testFilterCandidatesByStatusAndAdjudication_NoFilter() {
        List<CandidateDTO> expectedCandidates = new ArrayList<>();

        when(candidateService.getAllCandidates()).thenReturn(expectedCandidates);

        ResponseEntity<List<CandidateDTO>> response = candidateController.filterCandidatesByStatusAndAdjudication(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCandidates, response.getBody());

        verify(candidateService, times(1)).getAllCandidates();
    }

    @Test
    void testExportCandidates() {
        ExportRequestDTO requestDTO = new ExportRequestDTO();
        requestDTO.setStartDate(LocalDate.of(2022, 1, 1));
        requestDTO.setEndDate(LocalDate.of(2022, 12, 31));
        requestDTO.setEmail("test@example.com");

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setStatus(new StatusDTO("Cleared"));
        reportDTO.setAdjudication(new AdjudicationDTO("Pre-adverse"));

        LocalDateTime now = LocalDateTime.now();

        CandidateDTO candidateDTO1 = new CandidateDTO.CandidateBuilder()
                .setId(1)
                .setName("James")
                .setLocation("Bangalore")
                .setReport(reportDTO)
                .setCreationDate(now)
                .build();
        CandidateDTO candidateDTO2 = new CandidateDTO.CandidateBuilder()
                .setId(2)
                .setName("John")
                .setLocation("Delhi")
                .setReport(reportDTO)
                .setCreationDate(now)
                .build();

        List<CandidateDTO> candidates = List.of(candidateDTO1, candidateDTO2);

        when(candidateService.getCandidatesBetweenDates(requestDTO.getStartDate(), requestDTO.getEndDate()))
                .thenReturn(candidates);

        ResponseEntity<String> response = candidateController.exportCandidates(requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Download link was sent to your email: " + requestDTO.getEmail()));
        assertTrue(response.getBody().contains("CSV Content:"));
    }
    @Test
    void testGenerateCsvContent() {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setStatus(new StatusDTO("Cleared"));
        reportDTO.setAdjudication(new AdjudicationDTO("Pre-adverse"));

        CandidateDTO candidateDTO1 = new CandidateDTO.CandidateBuilder()
                .setId(1)
                .setName("James")
                .setLocation("Bangalore")
                .setReport(reportDTO)
                .setCreationDate(now())
                .build();
        CandidateDTO candidateDTO2 = new CandidateDTO.CandidateBuilder()
                .setId(2)
                .setName("John")
                .setLocation("Delhi")
                .setReport(reportDTO)
                .setCreationDate(now())
                .build();


        List<CandidateDTO> candidates = List.of(candidateDTO1, candidateDTO2);

        String csvContent = candidateController.generateCsvContent(candidates);

        String expectedCsvContent = "James, Bangalore, Pre-adverse, Cleared\n"
                + "John, Delhi, Pre-adverse, Cleared";

        csvContent = csvContent.replaceAll(", \\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{9}", "");
        expectedCsvContent = expectedCsvContent.replaceAll(", \\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{9}", "");

        assertEquals(expectedCsvContent, csvContent);
    }
    @Test
    void testSendEmailWithAttachment() {
        String attachmentContent = "Sample attachment content";

        String result = candidateController.sendEmailWithAttachment(attachmentContent);

        assertEquals(attachmentContent, result);
    }

    @Test
    void testSendPreAdverseActionNotification_CandidateFound() {
        CandidateDTO candidateDTO = new CandidateDTO();
        when(candidateService.getCandidateById(anyLong())).thenReturn(candidateDTO);

        PreAdverseNoticeDTO preAdverseNoticeDTO = new PreAdverseNoticeDTO();

        ResponseEntity<String> response = candidateController.sendPreAdverseActionNotification(1L, preAdverseNoticeDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pre-Adverse Action notice successfully sent.", response.getBody());
    }

    @Test
    void testSendPreAdverseActionNotification_candidateNotFound() {
        Long candidateId = 1L;

        when(candidateService.getCandidateById(candidateId)).thenReturn(null);

        PreAdverseNoticeDTO preAdverseNoticeDTO = new PreAdverseNoticeDTO();
        ResponseEntity<String> response = candidateController.sendPreAdverseActionNotification(candidateId, preAdverseNoticeDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Candidate not found with id: " + candidateId, response.getBody());
    }

    @Test
    void testCreateCandidate() {
        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setId(1);
        candidateDTO.setName("Dennis Richie");

        when(candidateService.createCandidate(candidateDTO)).thenReturn(candidateDTO);

        ResponseEntity<CandidateDTO> response = candidateController.createCandidate(candidateDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(candidateDTO, response.getBody());
    }

}
