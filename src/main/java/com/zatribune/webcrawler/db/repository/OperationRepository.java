package com.zatribune.webcrawler.db.repository;

import com.zatribune.webcrawler.db.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Long> {
}
