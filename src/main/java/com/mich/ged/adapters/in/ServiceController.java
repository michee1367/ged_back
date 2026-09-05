package com.mich.ged.adapters.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.interfaces.in.GererServiceUseCase;
import com.mich.ged.domain.models.ServiceModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/services")
@Tag(name="services", description="gerer les services")
public class ServiceController {

    private GererServiceUseCase  serviceUseCase;

    public ServiceController(
        GererServiceUseCase  serviceUseCase
    ) {
        this.serviceUseCase = serviceUseCase;
    }

    @GetMapping()
    @Operation(summary="fourni la liste des services")
    public ResponseEntity<PagedResult<ServiceModel>> donnerTous(
        @RequestParam(name="page", defaultValue="1") int page, 
        @RequestParam(name="per_page", defaultValue="1") int perPage 
    ) {

        PagedResult<ServiceModel> result = serviceUseCase.lister(page, perPage);

        return ResponseEntity.ok(result);

    }
}
