package com.zemoso.checkr.repository;

import com.zemoso.checkr.entities.Adjudication;
import com.zemoso.checkr.entities.Candidate;
import com.zemoso.checkr.entities.Report;
import com.zemoso.checkr.entities.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CandidateRepositoryTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Test
    void testFindByNameContainingIgnoreCase() {
        String name = "John";
        Candidate candidate = new Candidate();
        candidate.setName(name);
        List<Candidate> candidates = new ArrayList<>();
        candidates.add(candidate);

        when(candidateRepository.findByNameContainingIgnoreCase(name)).thenReturn(candidates);

        List<Candidate> foundCandidates = candidateRepository.findByNameContainingIgnoreCase(name);

        assertEquals(candidates, foundCandidates);
    }

    @Test
    void testFindByReport_Status_NameAndReport_Adjudication_Name() {
        String status = "Cleared";
        String adjudication = "Pre-adverse";

        Candidate candidate = new Candidate();

        Report report = new Report();

        Status reportStatus = new Status();
        reportStatus.setName(status);
        report.setStatus(reportStatus);

        Adjudication reportAdjudication = new Adjudication();
        reportAdjudication.setName(adjudication);
        report.setAdjudication(reportAdjudication);

        candidate.setReport(report);

        List<Candidate> candidates = new ArrayList<>();
        candidates.add(candidate);

        when(candidateRepository.findByReport_Status_NameAndReport_Adjudication_Name(status, adjudication)).thenReturn(candidates);

        List<Candidate> foundCandidates = candidateRepository.findByReport_Status_NameAndReport_Adjudication_Name(status, adjudication);

        assertEquals(candidates, foundCandidates);
    }

    @Test
    void testFindByReport_Status_Name() {
        String status = "Cleared";
        Candidate candidate = new Candidate();
        Report report = new Report();
        Status reportStatus = new Status();
        reportStatus.setName(status);
        report.setStatus(reportStatus);
        candidate.setReport(report);
        List<Candidate> candidates = List.of(candidate);

        when(candidateRepository.findByReport_Status_Name(status)).thenReturn(candidates);

        List<Candidate> foundCandidates = candidateRepository.findByReport_Status_Name(status);

        assertEquals(candidates, foundCandidates);
    }

    @Test
    void testFindByReport_Adjudication_Name() {
        String adjudication = "Pre-adverse";
        Candidate candidate = new Candidate();
        Report report = new Report();
        Adjudication reportAdjudication = new Adjudication();
        reportAdjudication.setName(adjudication);
        report.setAdjudication(reportAdjudication);
        candidate.setReport(report);
        List<Candidate> candidates = List.of(candidate);

        when(candidateRepository.findByReport_Adjudication_Name(adjudication)).thenReturn(candidates);

        List<Candidate> foundCandidates = candidateRepository.findByReport_Adjudication_Name(adjudication);

        assertEquals(candidates, foundCandidates);
    }

    @Test
    void testFindByReportCreationDateBetween() {
        LocalDateTime startDate = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2022, 12, 31, 23, 59);
        Candidate candidate = new Candidate();
        Report report = new Report();
        report.setCreationDate(LocalDateTime.of(2022, 6, 1, 12, 0));
        candidate.setReport(report);
        List<Candidate> candidates = List.of(candidate);

        when(candidateRepository.findByReportCreationDateBetween(startDate, endDate)).thenReturn(candidates);

        List<Candidate> foundCandidates = candidateRepository.findByReportCreationDateBetween(startDate, endDate);

        assertEquals(candidates, foundCandidates);
    }


}
