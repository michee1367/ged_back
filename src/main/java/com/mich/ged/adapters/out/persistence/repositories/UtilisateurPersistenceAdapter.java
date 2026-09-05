package com.mich.ged.adapters.out.persistence.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mich.ged.adapters.out.persistence.entities.UtilisateurEntity;
import com.mich.ged.adapters.out.persistence.mappers.GenericMapper;
import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.interfaces.out.UtilisateurRepositoryPort;
import com.mich.ged.domain.models.UtilisateurModel;

@Component
public class UtilisateurPersistenceAdapter implements UtilisateurRepositoryPort {

    private final SpringDataUtilisateurRepository springDataRepository;
    private final GenericMapper mapper;

    public UtilisateurPersistenceAdapter(SpringDataUtilisateurRepository springDataRepository,
                                         GenericMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public UtilisateurModel save(UtilisateurModel utilisateur) {
        UtilisateurEntity entity = mapper.toEntity(utilisateur);
        UtilisateurEntity saved = springDataRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UtilisateurModel> findById(Long idUtilisateur) {
        return springDataRepository.findById(idUtilisateur)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResult<UtilisateurModel> findAll(int page, int perPage) {

        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), perPage);
        Page<UtilisateurEntity> entityPage = springDataRepository.findAll(pageable);

        PagedResult<UtilisateurModel> result = mapper.withEntityPage(entityPage, mapper::toDomain);
        
        return result;
        
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UtilisateurModel> findByEmail(String email) {
        Optional<UtilisateurEntity> utilisateurOpt = springDataRepository.findByEmail(email);
        return utilisateurOpt.map(mapper::toDomain);
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UtilisateurModel> findByPhoneNumber(String phoneNumber) {
        Optional<UtilisateurEntity> entityOpt = springDataRepository.findByPhoneNumber(phoneNumber);
        return entityOpt.map(mapper::toDomain);
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UtilisateurModel> findByUsername(String username) {
        Optional<UtilisateurEntity> entity = springDataRepository.findByEmailOrPhoneNumber(username);
        return entity.map(mapper::toDomain);
        //throw new UnsupportedOperationException("Not supported yet.");
    }


}