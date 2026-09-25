package com.mich.ged.domain.interfaces.out;
import java.util.Optional;

import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.models.UtilisateurModel;

public interface UtilisateurRepositoryPort {
    UtilisateurModel save(UtilisateurModel dossier);
    UtilisateurModel update(UtilisateurModel dossier);
    Optional<UtilisateurModel> findById(Long idUtilisateur);
    Optional<UtilisateurModel> findByEmail(String email);
    Optional<UtilisateurModel> findByPhoneNumber(String email);
    PagedResult<UtilisateurModel> findAll(int page, int perPage);

    Optional<UtilisateurModel> findByUsername(String username);
    PagedResult<UtilisateurModel> findByAttachedUser(UtilisateurModel utilisateurModel, int page, int perPage);
    PagedResult<UtilisateurModel> findAllByService(Long idService, int page, int perPage);
    long countByAttachedUser(UtilisateurModel utilisateurModel);


}
