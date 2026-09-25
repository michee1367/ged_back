package com.mich.ged.domain.interfaces.out;
import java.util.List;
import java.util.Optional;

import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.models.DossierModel;
import com.mich.ged.domain.models.Priorite;
import com.mich.ged.domain.models.StatutDossier;
import com.mich.ged.domain.models.UtilisateurModel;

public interface DossierRepositoryPort {
    
    DossierModel save(DossierModel dossier);
    DossierModel update(DossierModel dossier);
    PagedResult<DossierModel> findAll(int page, int perPage);
    Optional<DossierModel> findById(Long idDossier);
    Optional<DossierModel> findByNumero(String numeroDossier);
    boolean existsByNumero(String numeroDossier);
    List<DossierModel> findByUser(UtilisateurModel utilisateurModel);
    PagedResult<DossierModel> findByUser(UtilisateurModel utilisateurModel, int page, int perPage);

    PagedResult<DossierModel> findByUserByStatus(UtilisateurModel utilisateurModel, StatutDossier statutDossier, int page, int perPage);

    long countParVisibilite(UtilisateurModel utilisateurModel);
    long countParVisibiliteEtStatut(UtilisateurModel utilisateurModel, StatutDossier statutDossier);
    long countParVisibiliteEtPrioriteIn(UtilisateurModel utilisateurModel, List<Priorite> priorites);
}
