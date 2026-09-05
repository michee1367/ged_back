package com.mich.ged.domain.interfaces.in;

import com.mich.ged.domain.models.DossierModel;

public interface TraiterDossierUseCase {
    DossierModel validerEtTraiter(TraiterDossierCommand command);
    DossierModel cloturer(Long idDossier, Long idAgentAction);


    /**
     * TraiterDossierCommand
     */
    public record TraiterDossierCommand(
        
    ) {
    }
    
}
