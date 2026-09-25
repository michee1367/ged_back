package com.mich.ged.domain.interfaces.out;
import java.util.Optional;

import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.models.ServiceModel;
import com.mich.ged.domain.models.UtilisateurModel;

public interface ServiceRepositoryPort {
    ServiceModel save(ServiceModel document);
    ServiceModel update(ServiceModel document);
    Optional<ServiceModel> findById(Long idDocument);
    PagedResult<ServiceModel> findAll(int page, int perPage);
    PagedResult<ServiceModel> findByAttachedUser(UtilisateurModel utilisateurModel, int page, int perPage);
    long countByAttachedUser(UtilisateurModel utilisateurModel);
    
}
