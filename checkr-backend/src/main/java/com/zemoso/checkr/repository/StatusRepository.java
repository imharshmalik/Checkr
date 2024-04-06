package com.zemoso.checkr.repository;

import com.zemoso.checkr.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
