package com.mich.ged.domain.models;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DossierModel
 */
public record DossierModel(
    Long idDossier,
    String numeroDossier,
    String objet,
    LocalDateTime dateReception,
    LocalDate echeance,
    Priorite priorite,
    StatutDossier statutActuel,
    UtilisateurModel agentResponsable,
    ServiceModel serviceActuel,
    List<DocumentModel> documents,
    List<HistoriqueTransmissionModel> historiques) {

    // Méthode de calcul d'urgence
    public boolean estEnRetard() {
        return echeance != null && LocalDate.now().isAfter(echeance) && statutActuel != StatutDossier.CLOTURE;
    }

    // Temps écoulé depuis la réception
    public Duration tempsEcoule() {
        return Duration.between(dateReception, LocalDateTime.now());
    }
}