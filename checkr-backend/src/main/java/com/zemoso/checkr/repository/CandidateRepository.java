package com.zemoso.checkr.repository;

import com.zemoso.checkr.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findByNameContainingIgnoreCase(String name);

    List<Candidate> findByReport_Status_NameAndReport_Adjudication_Name(String status, String adjudication);

    List<Candidate> findByReport_Status_Name(String status);

    List<Candidate> findByReport_Adjudication_Name(String adjudication);

    List<Candidate> findByReportCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
