package com.zemoso.checkr.repository;

import com.zemoso.checkr.entities.Adjudication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdjudicationRepository extends JpaRepository<Adjudication, Long> {
}
