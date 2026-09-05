package com.mich.ged.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.interfaces.in.GererServiceUseCase;
import com.mich.ged.domain.interfaces.out.ServiceRepositoryPort;
import com.mich.ged.domain.models.ServiceModel;

@Service
public class GererServiceImp implements GererServiceUseCase {
    @Autowired
    private ServiceRepositoryPort serviceRepo;

    @Override
    public PagedResult<ServiceModel> lister(int page, int perPage) {
        return serviceRepo.findAll(page, perPage);
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
