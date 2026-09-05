package com.mich.ged.domain.models;

import java.time.LocalDateTime;

/**
 * HistoriqueTransmissionModel
 */
public record HistoriqueTransmissionModel(
    Long idHistorique,
    DossierModel dossier,
    LocalDateTime dateHeureTransmission,
    UtilisateurModel expediteur,
    UtilisateurModel destinataire,
    ServiceModel serviceExpediteur,
    ServiceModel serviceDestinataire,
    String actionEffectuee,
    String commentaire,
    StatutDossier statutApresAction
) {
    public HistoriqueTransmissionModel {
        if (dateHeureTransmission == null) {
            dateHeureTransmission = LocalDateTime.now();
        }
    }
}