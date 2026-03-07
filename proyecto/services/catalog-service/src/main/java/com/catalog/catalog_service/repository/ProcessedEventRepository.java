package com.catalog.catalog_service.repository;
import com.catalog.catalog_service.model.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, String> {
}
