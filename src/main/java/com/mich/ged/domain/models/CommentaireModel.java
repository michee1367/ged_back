package com.mich.ged.domain.models;

public record CommentaireModel(
    Long idCommentaire,
    String contenu,
    DossierModel dossier,
    UtilisateurModel contributeur
) {
    
}
