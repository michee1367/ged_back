package com.mich.ged.adapters.out.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mich.ged.adapters.out.persistence.entities.ReponseEntity;

public interface SpringDataReponseRepository 
    extends JpaRepository<ReponseEntity, Long> 
{
    Optional<ReponseEntity> findFistByOrderByIdAsc();
    Optional<ReponseEntity> findFirstByDossierIdOrderByIdAsc(Long idDossier);
    
}
