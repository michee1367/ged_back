package com.mich.ged.application;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.mich.ged.domain.interfaces.in.TransmettreDossierUseCase;
import com.mich.ged.domain.interfaces.in.TransmettreDossierUseCase.TransmettreDossierCommand;
import com.mich.ged.domain.interfaces.out.DossierRepositoryPort;
import com.mich.ged.domain.interfaces.out.HistoriqueRepositoryPort;
import com.mich.ged.domain.interfaces.out.NotificationPort;
import com.mich.ged.domain.interfaces.out.ServiceRepositoryPort;
import com.mich.ged.domain.interfaces.out.UtilisateurRepositoryPort;
import com.mich.ged.domain.models.DossierModel;
import com.mich.ged.domain.models.HistoriqueTransmissionModel;
import com.mich.ged.domain.models.NotificationModel;
import com.mich.ged.domain.models.ServiceModel;
import com.mich.ged.domain.models.StatutDossier;
import com.mich.ged.domain.models.UtilisateurModel;

@Service
public class TransmettreDossierService implements TransmettreDossierUseCase {

    private final DossierRepositoryPort dossierRepo;
    private final HistoriqueRepositoryPort historiqueRepo;
    private final NotificationPort notificationPort;
    private final UtilisateurRepositoryPort utilisateurRepo;
    private final ServiceRepositoryPort serviceRepo;

    public TransmettreDossierService(DossierRepositoryPort dossierRepo,
                                     HistoriqueRepositoryPort historiqueRepo,
                                     UtilisateurRepositoryPort utilisateurRepo,
                                     ServiceRepositoryPort serviceRepo,
                                     NotificationPort notificationPort) {
        this.dossierRepo = dossierRepo;
        this.historiqueRepo = historiqueRepo;
        this.utilisateurRepo = utilisateurRepo;
        this.notificationPort = notificationPort;
        this.serviceRepo = serviceRepo;
    }

    @Override
    //@Transactional
    public DossierModel transmettre(TransmettreDossierCommand command) {
        UtilisateurModel agent = utilisateurRepo.findByUsername(
            command.nomUtilisateur()
        ).orElseThrow(() -> new IllegalArgumentException(
            "utilisateur introuvable avec l'ID : " + command.idDestinateur())
        );
        DossierModel dossier = dossierRepo.findById(command.idDossier())
            .orElseThrow(() -> new IllegalArgumentException("Dossier introuvable avec l'ID : " + command.idDossier()));

        UtilisateurModel destinateur = utilisateurRepo.findById(
            command.idDestinateur()
        ).orElseThrow(() -> new IllegalArgumentException(
            "utilisateur introuvable avec l'ID : " + command.idDestinateur())
        );

        
        ServiceModel serviceDestinataire = serviceRepo.findById(
            command.idServiceDestinataire()
        ).orElseThrow(() -> new IllegalArgumentException(
            "utilisateur introuvable avec l'ID : " + command.idServiceDestinataire())
        );
        

        // Mise à jour du statut
        DossierModel dossierMisAJour = new DossierModel(
            dossier.idDossier(),
            dossier.numeroDossier(),
            dossier.objet(),
            dossier.dateReception(),
            dossier.echeance(),
            dossier.priorite(),
            StatutDossier.EN_TRAITEMENT,
            dossier.agentResponsable(),
            serviceDestinataire,
            dossier.documents(),
            dossier.historiques()
        );

        dossierRepo.save(dossierMisAJour);

        // Horodatage automatique dans l'historique
        HistoriqueTransmissionModel trace = new HistoriqueTransmissionModel(
            null,
            dossier,
            LocalDateTime.now(),
            agent,
            destinateur,
            agent.service(),
            serviceDestinataire,
            "Transmission de dossier",
            command.observation(),
            StatutDossier.EN_TRAITEMENT
        );

        historiqueRepo.save(trace);

        // Envoi de notification au destinataire
        notificationPort.envoyerNotification(new NotificationModel(
            null,
            destinateur,
            serviceDestinataire,
            "Dossier " + dossier.numeroDossier() + " vous a été transmis.",
            LocalDateTime.now(),
            false
        ));

        return dossierMisAJour;
    }


    
}
