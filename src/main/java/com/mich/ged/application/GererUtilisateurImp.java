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
        ServiceModel service = serviceRepo.findById(command.idService()).orElseThrow(
            () -> new EntityNotFoundException("Le service n'exste pas")
        );

        String hashPwd = passwordEncoder.encode(command.motDePasse());
        System.out.println("###############################");
        System.out.println(command.motDePasse());
        System.out.println(hashPwd);
        System.out.println("###############################");
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
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UtilisateurModel modifierStatut(Long idUtilisateur, boolean actif) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PagedResult<UtilisateurModel> listerParService(Long idService) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PagedResult<UtilisateurModel> lister(int page, int perPage) {
        return utilisateurRepo.findAll(page, perPage);
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
