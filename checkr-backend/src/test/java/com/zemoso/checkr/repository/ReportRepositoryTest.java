package com.zemoso.checkr.repository;

import com.zemoso.checkr.entities.Report;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportRepositoryTest {
    @Mock
    private ReportRepository reportRepository;

    @Test
    void testFindById() {
        // Create a dummy report
        Report report = new Report();
        report.setId(1L);

        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));

        Optional<Report> result = reportRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(report, result.get());

        verify(reportRepository).findById(1L);
    }
}