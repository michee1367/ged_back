package com.mich.ged.domain.interfaces.in;

import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.models.ServiceModel;

public interface GererServiceUseCase {
    PagedResult<ServiceModel> lister(int page, int perPage);

    ServiceModel enregistrer(CreerServiceCommand data);

    ServiceModel modifier(Long idService, CreerServiceCommand data);

    public record CreerServiceCommand(
        Long idService,
        String nom,
        String code
    ) {

    }
}
