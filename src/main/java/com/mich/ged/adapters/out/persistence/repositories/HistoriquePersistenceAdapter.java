package com.mich.ged.adapters.out.persistence.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mich.ged.adapters.out.persistence.entities.HistoriqueTransmissionEntity;
import com.mich.ged.adapters.out.persistence.mappers.GenericMapper;
import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.interfaces.out.HistoriqueRepositoryPort;
import com.mich.ged.domain.models.HistoriqueTransmissionModel;

@Component
public class HistoriquePersistenceAdapter implements HistoriqueRepositoryPort {

    private final SpringDataHistoriqueRepository springDataRepository;
    private final GenericMapper mapper;

    public HistoriquePersistenceAdapter(SpringDataHistoriqueRepository springDataRepository,
                                         GenericMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public HistoriqueTransmissionModel save(HistoriqueTransmissionModel historique) {
        HistoriqueTransmissionEntity entity = mapper.toEntity(historique);
        HistoriqueTransmissionEntity saved = springDataRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResult<HistoriqueTransmissionModel> findByDossierId(Long idDossier, int page, int perPage) {
        Pageable pageable = PageRequest.of(page-1, perPage);

        Page<HistoriqueTransmissionEntity> entityPage = springDataRepository.findByDossierId(idDossier, pageable);

        return mapper.withEntityPage(entityPage, mapper::toDomain);
    }
}