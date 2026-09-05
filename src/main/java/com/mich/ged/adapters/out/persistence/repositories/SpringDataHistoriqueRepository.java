package com.mich.ged.adapters.out.persistence.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mich.ged.adapters.out.persistence.entities.HistoriqueTransmissionEntity;

@Repository
public interface SpringDataHistoriqueRepository extends JpaRepository<HistoriqueTransmissionEntity, Long> {
    List<HistoriqueTransmissionEntity> findByDossierId(Long dossierId);
    
    Page<HistoriqueTransmissionEntity> findByDossierId(Long dossierId, Pageable pageable);
}