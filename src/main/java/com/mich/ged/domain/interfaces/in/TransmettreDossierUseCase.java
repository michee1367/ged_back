package com.mich.ged.domain.interfaces.in;

import com.mich.ged.domain.models.DossierModel;

public interface TransmettreDossierUseCase {
    DossierModel transmettre(TransmettreDossierCommand command);

    /**
     * TransmettreDossierCommand
     */
    public record TransmettreDossierCommand(
        Long idDossier, 
        Long idDestinateur,
        Long idServiceDestinataire, 
        String observation,
        String nomUtilisateur
    ) {
        public TransmettreDossierCommand(
            Long idDossier, 
        Long idDestinateur,
        Long idServiceDestinataire, 
        String observation
        ) {
            this(
                idDossier, idDestinateur, 
                idServiceDestinataire, observation, 
                null);
        }

        public TransmettreDossierCommand withUserName(
            String nomUtilisateur
        ){
            return new TransmettreDossierCommand(
                idDossier, idDestinateur, 
                idServiceDestinataire, observation,
                nomUtilisateur
            );
        }
        public TransmettreDossierCommand withId(
            Long idDossier
        ){
            return new TransmettreDossierCommand(
                idDossier, idDestinateur, 
                idServiceDestinataire, observation,
                nomUtilisateur
            );
        }
    }
}
