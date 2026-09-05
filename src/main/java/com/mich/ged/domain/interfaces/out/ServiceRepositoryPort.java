package com.mich.ged.domain.interfaces.out;
import java.util.Optional;

import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.models.ServiceModel;

public interface ServiceRepositoryPort {
    ServiceModel save(ServiceModel document);
    Optional<ServiceModel> findById(Long idDocument);
    PagedResult<ServiceModel> findAll(int page, int perPage);
    
}
