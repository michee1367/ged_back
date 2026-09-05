package com.mich.ged.domain.interfaces.in;

import java.util.List;

import com.mich.ged.domain.models.DossierModel;

public interface RechercherDossierUseCase {
    
    List<DossierModel> rechercher(RechercheDossierQuery query);
    DossierModel rechercherParNumero(String numeroDossier);


    /**
     * RechercheDossierQuery
     */
    public record RechercheDossierQuery() {
    }
    
}
