package com.mich.ged.domain.models;

/**
 * Vue réduite d'un utilisateur pour les listes de sélection.
 * Ne contient aucune donnée sensible.
 */
public record UtilisateurBrefModel(
    Long idUtilisateur,
    String nomComplet
) {
}
