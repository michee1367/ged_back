package com.mich.ged.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mich.ged.domain.interfaces.in.DashboardUseCase;
import com.mich.ged.domain.interfaces.out.DocumentRepositoryPort;
import com.mich.ged.domain.interfaces.out.DossierRepositoryPort;
import com.mich.ged.domain.interfaces.out.ServiceRepositoryPort;
import com.mich.ged.domain.interfaces.out.UtilisateurRepositoryPort;
import com.mich.ged.domain.models.Priorite;
import com.mich.ged.domain.models.StatutDossier;
import com.mich.ged.domain.models.UtilisateurModel;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DashboardService implements DashboardUseCase {

    private final UtilisateurRepositoryPort utilisateurRepo;
    private final DossierRepositoryPort dossierRepo;
    private final DocumentRepositoryPort documentRepo;
    private final ServiceRepositoryPort serviceRepo;

    public DashboardService(UtilisateurRepositoryPort utilisateurRepo,
                            DossierRepositoryPort dossierRepo,
                            DocumentRepositoryPort documentRepo,
                            ServiceRepositoryPort serviceRepo) {
        this.utilisateurRepo = utilisateurRepo;
        this.dossierRepo = dossierRepo;
        this.documentRepo = documentRepo;
        this.serviceRepo = serviceRepo;
    }

    @Override
    public KpisDashboard getKpis(String idUtilisateur) {
        UtilisateurModel utilisateur = utilisateurRepo.findByUsername(idUtilisateur).orElseThrow(
            () -> new EntityNotFoundException("Utilisateur n'existe pas")
        );

        return new KpisDashboard(
            (int) dossierRepo.countParVisibilite(utilisateur),
            (int) documentRepo.countParVisibilite(utilisateur),
            (int) serviceRepo.countByAttachedUser(utilisateur),
            (int) utilisateurRepo.countByAttachedUser(utilisateur),
            (int) dossierRepo.countParVisibiliteEtStatut(utilisateur, StatutDossier.EN_TRAITEMENT),
            (int) dossierRepo.countParVisibiliteEtStatut(utilisateur, StatutDossier.TRAITE),
            (int) dossierRepo.countParVisibiliteEtStatut(utilisateur, StatutDossier.CLOTURE),
            (int) dossierRepo.countParVisibiliteEtPrioriteIn(utilisateur, List.of(Priorite.URGENTE, Priorite.TRES_URGENTE))
        );
    }

}