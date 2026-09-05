package com.mich.ged.domain.models;

public record ReponseModel(
    Long idReponse,
    String contenu,
    DossierModel dossier
) {

    public ReponseModel changeConetenu(String newContenu){
        return new ReponseModel(
            this.idReponse,
            newContenu,
            this.dossier
        );
    } 
    
}
