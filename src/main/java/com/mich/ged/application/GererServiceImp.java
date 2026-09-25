package com.mich.ged.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.interfaces.in.GererServiceUseCase;
import com.mich.ged.domain.interfaces.out.ServiceRepositoryPort;
import com.mich.ged.domain.models.ServiceModel;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GererServiceImp implements GererServiceUseCase {
    @Autowired
    private ServiceRepositoryPort serviceRepo;

    @Override
    public PagedResult<ServiceModel> lister(int page, int perPage) {
        return serviceRepo.findAll(page, perPage);
    }

    @Override
    public ServiceModel enregistrer(CreerServiceCommand data) {
        ServiceModel service = new ServiceModel(
            data.idService(),
            data.nom(),
            data.code()
        );

        return serviceRepo.save(service);
    }

    @Override
    public ServiceModel modifier(Long idService, CreerServiceCommand data) {
        ServiceModel existant = serviceRepo.findById(idService).orElseThrow(
            () -> new EntityNotFoundException("Le service n'existe pas")
        );

        ServiceModel service = new ServiceModel(
            existant.idService(),
            data.nom(),
            data.code()
        );

        return serviceRepo.update(service);
    }
}
