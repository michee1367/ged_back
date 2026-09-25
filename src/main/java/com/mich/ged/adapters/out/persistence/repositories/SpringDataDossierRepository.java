package com.mich.ged.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mich.ged.adapters.out.persistence.entities.DossierEntity;
import com.mich.ged.domain.models.StatutDossier;

@Repository
public interface SpringDataDossierRepository extends JpaRepository<DossierEntity, Long> {
    
    Optional<DossierEntity> findByNumero(String numero);
    boolean existsByNumero(String numero);
@Query(value = "SELECT DISTINCT d.* FROM dossiers d " +
           "INNER JOIN historique_transmissions t ON d.id = t.dossier_id " +
           "WHERE (:isSecretaire = true AND t.service_destinataire_id = :serviceId) " +
           "OR (:isSecretaire = false AND (t.destinataire_user_id = :userId))",
           nativeQuery = true)
    List<DossierEntity> findDossiersParVisibilite(
        @Param("userId") Long userId,
        @Param("serviceId") Long serviceId,
        @Param("isSecretaire") boolean isSecretaire
    );
    @Query(value = "SELECT DISTINCT d.* FROM dossiers d " +
           "INNER JOIN historique_transmissions t ON d.id = t.dossier_id " +
           "WHERE (:isSecretaire = true AND t.service_destinataire_id = :serviceId) " +
           "OR (:isSecretaire = false AND (t.destinataire_user_id = :userId OR t.service_destinataire_id = :serviceId))",
           countQuery = "SELECT COUNT(DISTINCT d.id) FROM dossiers d " +
                        "INNER JOIN historique_transmissions t ON d.id = t.dossier_id " +
                        "WHERE (:isSecretaire = true AND t.service_destinataire_id = :serviceId) " +
                        "OR (:isSecretaire = false AND (t.destinataire_user_id = :userId OR t.service_destinataire_id = :serviceId))",
           nativeQuery = true)
    Page<DossierEntity> findDossiersParVisibilite(
        @Param("userId") Long userId,
        @Param("serviceId") Long serviceId,
        @Param("isSecretaire") boolean isSecretaire,
        Pageable pageable
    );

    @Query(value = "SELECT DISTINCT d.* FROM dossiers d " +
           "INNER JOIN historique_transmissions t ON d.id = t.dossier_id " +
           "WHERE ((:isSecretaire = true AND t.service_destinataire_id = :serviceId) " +
           "OR (:isSecretaire = false AND (t.destinataire_user_id = :userId OR t.service_destinataire_id = :serviceId))) " +
           "AND d.statut = :statut",
           countQuery = "SELECT COUNT(DISTINCT d.id) FROM dossiers d " +
                        "INNER JOIN historique_transmissions t ON d.id = t.dossier_id " +
                        "WHERE ((:isSecretaire = true AND t.service_destinataire_id = :serviceId) " +
                        "OR (:isSecretaire = false AND (t.destinataire_user_id = :userId OR t.service_destinataire_id = :serviceId))) " +
                        "AND d.statut = :statut",
           nativeQuery = true)
    Page<DossierEntity> findDossiersParVisibiliteEtStatut(
        @Param("userId") Long userId,
        @Param("serviceId") Long serviceId,
        @Param("isSecretaire") boolean isSecretaire,
        @Param("statut") String statut,
        Pageable pageable
    );

    @Query(value = "SELECT COUNT(DISTINCT d.id) FROM dossiers d " +
           "INNER JOIN historique_transmissions t ON d.id = t.dossier_id " +
           "WHERE (:isSecretaire = true AND t.service_destinataire_id = :serviceId) " +
           "OR (:isSecretaire = false AND (t.destinataire_user_id = :userId OR t.service_destinataire_id = :serviceId))",
           nativeQuery = true)
    long countDossiersParVisibilite(
        @Param("userId") Long userId,
        @Param("serviceId") Long serviceId,
        @Param("isSecretaire") boolean isSecretaire
    );

    @Query(value = "SELECT COUNT(DISTINCT d.id) FROM dossiers d " +
           "INNER JOIN historique_transmissions t ON d.id = t.dossier_id " +
           "WHERE ((:isSecretaire = true AND t.service_destinataire_id = :serviceId) " +
           "OR (:isSecretaire = false AND (t.destinataire_user_id = :userId OR t.service_destinataire_id = :serviceId))) " +
           "AND d.statut = :statut",
           nativeQuery = true)
    long countDossiersParVisibiliteEtStatut(
        @Param("userId") Long userId,
        @Param("serviceId") Long serviceId,
        @Param("isSecretaire") boolean isSecretaire,
        @Param("statut") String statut
    );

    @Query(value = "SELECT COUNT(DISTINCT d.id) FROM dossiers d " +
           "INNER JOIN historique_transmissions t ON d.id = t.dossier_id " +
           "WHERE ((:isSecretaire = true AND t.service_destinataire_id = :serviceId) " +
           "OR (:isSecretaire = false AND (t.destinataire_user_id = :userId OR t.service_destinataire_id = :serviceId))) " +
           "AND d.priorite IN (:priorites)",
           nativeQuery = true)
    long countDossiersParVisibiliteEtPrioriteIn(
        @Param("userId") Long userId,
        @Param("serviceId") Long serviceId,
        @Param("isSecretaire") boolean isSecretaire,
        @Param("priorites") List<String> priorites
    );
    
}
