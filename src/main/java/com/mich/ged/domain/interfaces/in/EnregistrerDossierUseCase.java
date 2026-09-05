package com.mich.ged.domain.interfaces.in;

import java.time.LocalDate;

import com.mich.ged.domain.models.DocumentModel;
import com.mich.ged.domain.models.DossierModel;
import com.mich.ged.domain.models.Priorite;

public interface EnregistrerDossierUseCase {

    DossierModel enregistrer(EnregistrerDossierCommand command);

    DocumentModel joindreDocument(JoindreDocumentCommand command);


    /**
     * EnregistrerDossierCommand
     */
    public record EnregistrerDossierCommand(
        String objet,
        Long expediteurOrigineId,
        LocalDate echeance,
        Priorite priorite,
        String nomUtilisateur
    ) {
        public EnregistrerDossierCommand(
            String objet,
        Long expediteurOrigineId,
        LocalDate echeance,
        Priorite priorite
        ) {
            this(
                objet, 
                expediteurOrigineId, 
                echeance, 
                priorite, 
                null
            );
        }

    }

    public record JoindreDocumentCommand(
        String nom, 
        String titre, 
        byte[] contenu,
        Long idDossier
    ) {

    }
}
