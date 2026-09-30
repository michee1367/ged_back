package com.mich.ged.adapters.out.persistence.repositories;

/**
 * Projection légère utilisée par l'annuaire : seules les colonnes nécessaires
 * au nom sont lues, sans la collection de rôles (EAGER) ni le service (LAZY).
 */
public interface UtilisateurProjectionIdNom {

    Long getIdUtilisateur();

    String getNom();

    String getPostNom();

    String getPrenom();
}
