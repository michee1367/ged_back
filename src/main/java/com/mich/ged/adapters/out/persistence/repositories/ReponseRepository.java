package com.mich.ged.adapters.out.persistence.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mich.ged.adapters.out.persistence.entities.ReponseEntity;
import com.mich.ged.adapters.out.persistence.mappers.GenericMapper;
import com.mich.ged.domain.interfaces.out.ReponseRepositoryPort;
import com.mich.ged.domain.models.DossierModel;
import com.mich.ged.domain.models.ReponseModel;
@Repository
public class ReponseRepository implements ReponseRepositoryPort {

    @Autowired
    GenericMapper mapper;

    @Autowired
    SpringDataReponseRepository jpaRepo;

    @Override
    @Transactional
    public ReponseModel save(ReponseModel reponseModel) {


        // Mapping model to entity

        ReponseEntity entity = mapper.toEntity(reponseModel);

        System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{");
        System.out.println(reponseModel.idReponse());
        System.out.println(entity.getId());
        System.out.println("}}}}}}}}}}}}}}}}}}}}}}}}");

        // fin mapping

        // save

        ReponseEntity entitySave = jpaRepo.save(entity);

        // entity - model

        ReponseModel model = mapper.toDomain(entitySave);

        return model;

        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly=true)
    public Optional<ReponseModel> findById(Long id) {

        Optional<ReponseEntity> entityOpt = jpaRepo.findById(id);

        return entityOpt.map(mapper::toDomain);

        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly=true)
    public Optional<ReponseModel> findOne() {
        Optional<ReponseEntity> result = jpaRepo.findFistByOrderByIdAsc();
        return result.map(mapper::toDomain);
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly=true)
    public Optional<ReponseModel> findByDossier(DossierModel dossier) {
        Optional<ReponseEntity> result = jpaRepo.findFirstByDossierIdOrderByIdAsc(dossier.idDossier());
        return result.map(mapper::toDomain);
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
