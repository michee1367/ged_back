package com.mich.ged.domain.models;

import java.util.Set;

/**
 * UtilisateurModel
 */
public record UtilisateurModel(
    Long idUtilisateur,
    String nom,
    String postNom,
    String prenom,
    String phoneNumber,
    String email,
    String motDePasse,
    Set<TypeRole> roles,
    Boolean actif,
    ServiceModel service
) {
    // Méthode utilitaire directe sur le Record
    public String nomComplet() {
        return prenom + " " + nom;
    }
}