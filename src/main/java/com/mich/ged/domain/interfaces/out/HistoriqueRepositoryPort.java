package com.mich.ged.domain.interfaces.out;

import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.models.HistoriqueTransmissionModel;

public interface HistoriqueRepositoryPort {
    
    HistoriqueTransmissionModel save(HistoriqueTransmissionModel historique);
    PagedResult<HistoriqueTransmissionModel> findByDossierId(Long idDossier, int page, int perPage);
    
}
