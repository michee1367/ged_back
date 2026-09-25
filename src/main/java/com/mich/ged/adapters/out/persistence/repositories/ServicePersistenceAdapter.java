package com.mich.ged.adapters.out.persistence.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mich.ged.adapters.out.persistence.entities.ServiceEntity;
import com.mich.ged.adapters.out.persistence.mappers.GenericMapper;
import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.interfaces.out.ServiceRepositoryPort;
import com.mich.ged.domain.models.ServiceModel;
import com.mich.ged.domain.models.UtilisateurModel;

import jakarta.persistence.EntityNotFoundException;

@Component
public class ServicePersistenceAdapter implements ServiceRepositoryPort {

    private final SpringDataServiceRepository springDataRepository;
    private final GenericMapper mapper;

    public ServicePersistenceAdapter(SpringDataServiceRepository springDataRepository,
                                      GenericMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public ServiceModel save(ServiceModel service) {
        ServiceEntity entity = mapper.toEntity(service);
        ServiceEntity saved = springDataRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional
    public ServiceModel update(ServiceModel service) {
        ServiceEntity entity = springDataRepository.findById(service.idService())
                .orElseThrow(() -> new EntityNotFoundException("Le service n'existe pas"));
        entity = mapper.toEntity(service, entity);

        ServiceEntity saved = springDataRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceModel> findById(Long idService) {
        return springDataRepository.findById(idService)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResult<ServiceModel> findAll(int page, int perPage) {
        Pageable pageable = PageRequest.of(page-1, perPage);
        Page<ServiceEntity> pageEntity = springDataRepository.findAll(pageable);
        
        return mapper.withEntityPage(pageEntity, mapper::toDomain);
        
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResult<ServiceModel> findByAttachedUser(UtilisateurModel utilisateurModel, int page, int perPage) {
        if (utilisateurModel.service() == null) {
            return PagedResult.empty();
        }
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), perPage);
        Page<ServiceEntity> pageEntity = springDataRepository.findAttachedServices(
            utilisateurModel.service().idService(), pageable
        );
        return mapper.withEntityPage(pageEntity, mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByAttachedUser(UtilisateurModel utilisateurModel) {
        if (utilisateurModel.service() == null) {
            return 0;
        }
        return springDataRepository.countAttachedServices(utilisateurModel.service().idService());
    }
}