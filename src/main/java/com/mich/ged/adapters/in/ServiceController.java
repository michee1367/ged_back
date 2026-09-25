package com.mich.ged.adapters.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping()
    @Operation (summary="Crée un service")
    public ResponseEntity<ServiceModel> enregistrer(
        @RequestBody CreerServiceDto data
    ) {
        var service = this.serviceUseCase.enregistrer(new GererServiceUseCase.CreerServiceCommand(
            null,
            data.nom(),
            data.code()
        ));

        return ResponseEntity.status(201).body(service);
    }

    @PutMapping("/{id}")
    @Operation (summary="Modifie un service")
    public ResponseEntity<ServiceModel> modifier(
        @PathVariable(name="id") Long idService,
        @RequestBody CreerServiceDto data
    ) {
        var service = this.serviceUseCase.modifier(idService, new GererServiceUseCase.CreerServiceCommand(
            idService,
            data.nom(),
            data.code()
        ));

        return ResponseEntity.ok(service);
    }

    record CreerServiceDto(
        String nom,
        String code
    ) {

    }
}
