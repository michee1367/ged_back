package com.mich.ged.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.interfaces.in.GererUtilisateursUseCase;
import com.mich.ged.domain.interfaces.out.ServiceRepositoryPort;
import com.mich.ged.domain.interfaces.out.UtilisateurRepositoryPort;
import com.mich.ged.domain.models.ServiceModel;
import com.mich.ged.domain.models.UtilisateurModel;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GererUtilisateurImp implements GererUtilisateursUseCase {
    @Autowired
    private UtilisateurRepositoryPort utilisateurRepo;
    @Autowired
    private ServiceRepositoryPort serviceRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UtilisateurModel creerUtilisateur(CreerUtilisateurCommand command) {
        /*ServiceModel service = serviceRepo.findById(command.idService()).orElseThrow(
            () -> new EntityNotFoundException("Le service n'exste pas")
        );*/
        ServiceModel service = null;
        if(command.idService() != null) {
            service = serviceRepo.findById(command.idService()).orElse(null);
        }

        String hashPwd = passwordEncoder.encode(command.motDePasse());
        UtilisateurModel model = new UtilisateurModel(
            null,
            command.nom(),
            command.postNom(),
            command.prenom(),
            command.phoneNumber(),
            command.email(),
            hashPwd,
            command.roles(),
            true,
            service
        );

        UtilisateurModel utilisateurSave = utilisateurRepo.save(model);

        return utilisateurSave;
    }

    @Override
    public UtilisateurModel modifier(Long idUtilisateur, ModifierUtilisateurCommand command) {

        UtilisateurModel existant = utilisateurRepo.findById(idUtilisateur).orElseThrow(
            () -> new EntityNotFoundException("L'utilisateur n'existe pas")
        );

        ServiceModel service = serviceRepo.findById(command.idService()).orElseThrow(
            () -> new EntityNotFoundException("Le service n'existe pas")
        );

        String hashPwd = (command.motDePasse() != null && !command.motDePasse().isBlank())
            ? passwordEncoder.encode(command.motDePasse())
            : existant.motDePasse();

        UtilisateurModel model = new UtilisateurModel(
            idUtilisateur,
            command.nom(),
            command.postNom(),
            command.prenom(),
            command.phoneNumber(),
            command.email(),
            hashPwd,
            command.roles() != null ? command.roles() : existant.roles(),
            command.actif() != null ? command.actif() : existant.actif(),
            service
        );

        return utilisateurRepo.save(model);
    }

    @Override
    public UtilisateurModel modifierStatut(Long idUtilisateur, boolean actif) {
        UtilisateurModel existant = utilisateurRepo.findById(idUtilisateur).orElseThrow(
            () -> new EntityNotFoundException("L'utilisateur n'existe pas")
        );

        UtilisateurModel model = new UtilisateurModel(
            existant.idUtilisateur(),
            existant.nom(),
            existant.postNom(),
            existant.prenom(),
            existant.phoneNumber(),
            existant.email(),
            existant.motDePasse(),
            existant.roles(),
            actif,
            existant.service()
        );

        return utilisateurRepo.save(model);
    }

    @Override
    public PagedResult<UtilisateurModel> listerParService(Long idService) {
        return utilisateurRepo.findAllByService(idService, 1, 10);
    }

    @Override
    public PagedResult<UtilisateurModel> lister(int page, int perPage) {
        return utilisateurRepo.findAll(page, perPage);
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
