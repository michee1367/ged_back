package com.mich.ged.domain.models;
import java.time.LocalDateTime;

/**
 * DocumentModel
 */
public record DocumentModel(
    Long idDocument,
    String nomFichier,
    String cheminStockage,
    LocalDateTime dateAjout,
    String format,
    DossierModel dossier
) {
}