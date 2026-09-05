package com.mich.ged.adapters.out.persistence.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mich.ged.adapters.out.persistence.entities.DocumentEntity;

@Repository
public interface SpringDataDocumentRepository extends JpaRepository<DocumentEntity, Long> {

    Page<DocumentEntity> findByDossierId(Long idDossier, Pageable pageable);
}