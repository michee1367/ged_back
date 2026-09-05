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
}