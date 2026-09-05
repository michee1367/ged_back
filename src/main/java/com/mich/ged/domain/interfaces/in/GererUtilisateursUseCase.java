package com.mich.ged.domain.interfaces.in;

import java.util.Set;

import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.models.TypeRole;
import com.mich.ged.domain.models.UtilisateurModel;

public interface GererUtilisateursUseCase {

    UtilisateurModel creerUtilisateur(CreerUtilisateurCommand command);
    UtilisateurModel modifierStatut(Long idUtilisateur, boolean actif);
    
    PagedResult<UtilisateurModel> listerParService(Long idService);
    PagedResult<UtilisateurModel> lister(int page, int perPage);

    /**
     * CreerUtilisateurCommand
     */
    public record CreerUtilisateurCommand(
        Long idUtilisateur,
        String nom,
        String postNom,
        String prenom,
        String phoneNumber,
        String email,
        String motDePasse,
        Set<TypeRole> roles,
        Long idService
        
    ) {
        
    }
    
}
