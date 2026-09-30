package com.mich.ged.domain.models;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record UtilisateurModel(
    Long idUtilisateur,
    String nom,
    String postNom,
    String prenom,
    String phoneNumber,
    String email,
    @JsonIgnore String motDePasse,
    Set<TypeRole> roles,
    Boolean actif,
    ServiceModel service
) {
    // Méthode utilitaire directe sur le Record
    public String nomComplet() {
        return prenom + " " + nom;
    }

    /**
     * Format administratif : nom postnom prénom.
     * Les parties nulles ou vides sont simplement omises.
     */
    public String nomCompletAvecPostNom() {
        return Stream.of(nom, postNom, prenom)
                .filter(part -> part != null && !part.isBlank())
                .map(String::strip)
                .collect(Collectors.joining(" "));
    }
}
