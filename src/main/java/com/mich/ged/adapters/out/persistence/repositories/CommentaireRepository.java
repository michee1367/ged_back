package com.mich.ged.adapters.out.persistence.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mich.ged.adapters.out.persistence.entities.CommentaireEntity;
import com.mich.ged.adapters.out.persistence.mappers.GenericMapper;
import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.interfaces.out.CommentaireRepositoryPort;
import com.mich.ged.domain.models.CommentaireModel;
import com.mich.ged.domain.models.DossierModel;


@Repository
public class CommentaireRepository implements CommentaireRepositoryPort {
    @Autowired
    SpringDataCommentaireRepository jpaRepo;
    @Autowired
    GenericMapper mapper;

    @Override
    @Transactional()
    public CommentaireModel save(CommentaireModel model) {
        // mapping model to entity
        CommentaireEntity entity = mapper.toEntity(model);

        // and maping 
        // save
        CommentaireEntity entitySave = jpaRepo.save(entity);
        // end save
        // mapping entity to model
        CommentaireModel newModel = mapper.toDomain(entitySave);

        return newModel;

        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentaireModel> findById(Long id) {
        
        Optional<CommentaireEntity> entity = jpaRepo.findById(id);
        return entity.map(mapper::toDomain);
        
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResult<CommentaireModel> findByDosier(DossierModel dossier, int page, int per_page) {
        Pageable pageable = PageRequest.of(Math.max(0, page-1), per_page);
        
        Page<CommentaireEntity> result = jpaRepo.findByDossierIdOrderByIdAsc(dossier.idDossier(), pageable);

        return mapper.withEntityPage(result, mapper::toDomain);
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
