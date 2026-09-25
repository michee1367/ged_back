package com.mich.ged.adapters.out.persistence.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mich.ged.adapters.out.persistence.entities.DocumentEntity;

@Repository
public interface SpringDataDocumentRepository extends JpaRepository<DocumentEntity, Long> {

    Page<DocumentEntity> findByDossierId(Long idDossier, Pageable pageable);

    @Query(value = "SELECT DISTINCT d.* FROM documents d " +
           "WHERE :userId = d.id ",
           countQuery = "SELECT COUNT(DISTINCT d.id) FROM documents d " +
                        "WHERE :userId = d.id ",
           nativeQuery = true)
    Page<DocumentEntity> findByUser(
        @Param("userId") Long userId,
        Pageable pageable
    );

    @Query(value = "SELECT COUNT(DISTINCT d.id) FROM documents d " +
           "INNER JOIN dossiers dd ON d.dossier_id = dd.id " +
           "INNER JOIN historique_transmissions t ON dd.id = t.dossier_id " +
           "WHERE (:isSecretaire = true AND t.service_destinataire_id = :serviceId) " +
           "OR (:isSecretaire = false AND (t.destinataire_user_id = :userId OR t.service_destinataire_id = :serviceId))",
           nativeQuery = true)
    long countDocumentsParVisibilite(
        @Param("userId") Long userId,
        @Param("serviceId") Long serviceId,
        @Param("isSecretaire") boolean isSecretaire
    );
}