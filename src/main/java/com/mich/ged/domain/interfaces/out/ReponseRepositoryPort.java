package com.mich.ged.domain.interfaces.out;

import java.util.Optional;

import com.mich.ged.domain.models.DossierModel;
import com.mich.ged.domain.models.ReponseModel;

public interface ReponseRepositoryPort {
    ReponseModel save(ReponseModel commentaireModel);
    Optional<ReponseModel> findById(Long id);
    Optional<ReponseModel> findOne();
    Optional<ReponseModel> findByDossier(DossierModel dossier);
}