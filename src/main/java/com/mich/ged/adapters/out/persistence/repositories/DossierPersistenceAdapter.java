package com.mich.ged.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mich.ged.adapters.out.persistence.entities.DossierEntity;
import com.mich.ged.adapters.out.persistence.mappers.GenericMapper;
import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.interfaces.out.DossierRepositoryPort;
import com.mich.ged.domain.models.DossierModel;
import com.mich.ged.domain.models.TypeRole;
import com.mich.ged.domain.models.UtilisateurModel;

@Component
public class DossierPersistenceAdapter implements  DossierRepositoryPort {

    private final SpringDataDossierRepository springDataRepository;
    private final GenericMapper mapper;

    public DossierPersistenceAdapter(SpringDataDossierRepository springDataRepository,
                                   GenericMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public DossierModel save(DossierModel dossier) {
        DossierEntity entity = mapper.toEntity(dossier);
        DossierEntity savedEntity = springDataRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    

    @Override
    @Transactional(readOnly = true)
    public PagedResult<DossierModel> findAll(int page, int perPage) {
        // En Spring Data, les pages commencent à l'index 0
        int pageIndex = Math.max(page - 1, 0);
        Pageable pageable = PageRequest.of(pageIndex, perPage);
        Page<DossierEntity> entityPage = springDataRepository.findAll(pageable);

        List<DossierModel> content = entityPage.getContent()
                .stream()
                .map(mapper::toDomain)
                .toList();

        return new PagedResult<>(
                content,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements(),
                entityPage.getTotalPages(),
                entityPage.isLast()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DossierModel> findById(Long idDossier) {
        return springDataRepository.findById(idDossier)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DossierModel> findByNumero(String numeroDossier) {
        return springDataRepository.findByNumero(numeroDossier)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNumero(String numeroDossier) {
        return springDataRepository.existsByNumero(numeroDossier);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DossierModel> findByUser(UtilisateurModel utilisateurModel) {
        return springDataRepository.findDossiersParVisibilite(
            utilisateurModel.idUtilisateur(), 
            utilisateurModel.service().idService(), 
            utilisateurModel.roles().contains(TypeRole.SECRETAIRE_BUREAU)
        ).stream().map(mapper::toDomain).toList();
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResult<DossierModel> findByUser(UtilisateurModel utilisateurModel, int page, int perPage) {
        int pageIndex = Math.max(page - 1, 0);
        Pageable pageable = PageRequest.of(pageIndex, perPage);
        
        Page<DossierEntity> entityPage = springDataRepository.findDossiersParVisibilite(
            utilisateurModel.idUtilisateur(), 
            utilisateurModel.service().idService(), 
            utilisateurModel.roles().contains(TypeRole.SECRETAIRE_BUREAU),
            pageable
        );

        List<DossierModel> content = entityPage.getContent()
                .stream()
                .map(mapper::toDomain)
                .toList();

        return new PagedResult<>(
                content,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements(),
                entityPage.getTotalPages(),
                entityPage.isLast()
        );

        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
