package com.mich.ged.adapters.out.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mich.ged.adapters.out.persistence.entities.CommentaireEntity;
import com.mich.ged.adapters.out.persistence.entities.ReponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpringDataCommentaireRepository 
    extends JpaRepository<CommentaireEntity, Long> 
{
    Page<CommentaireEntity> findByDossierIdOrderByIdAsc(Long idDossier, Pageable pageable);
}
