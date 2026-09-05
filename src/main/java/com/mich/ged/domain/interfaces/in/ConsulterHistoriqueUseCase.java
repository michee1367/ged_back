package com.mich.ged.domain.interfaces.in;

import java.util.List;

import com.mich.ged.domain.models.HistoriqueTransmissionModel;

public interface ConsulterHistoriqueUseCase {
    List<HistoriqueTransmissionModel> obtenirHistoriqueComplet(Long idDossier);   
}
