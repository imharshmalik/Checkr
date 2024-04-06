package com.zemoso.checkr.repository;

import com.zemoso.checkr.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
