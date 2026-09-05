package com.mich.ged.adapters.in;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.interfaces.in.GererUtilisateursUseCase;
import com.mich.ged.domain.interfaces.in.GererUtilisateursUseCase.CreerUtilisateurCommand;
import com.mich.ged.domain.models.UtilisateurModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * UtilisateurController
 */
@RestController
@RequestMapping("/utilisateurs")
@Tag(name="utilisateurs", description="gerer les utilsateurs")
public class UtilisateurController {

    private GererUtilisateursUseCase gererUtilisateursUseCase;

    public UtilisateurController(
        GererUtilisateursUseCase gererUtilisateursUseCase
    ) {
        this.gererUtilisateursUseCase = gererUtilisateursUseCase;
    }

    @Operation(summary="fourni les utilisateurs")
    //@SecurityRequirement(name="jwt")
    //@Parameter(name="Authorization", in = ParameterIn.HEADER, required=true)
    @GetMapping()
    public ResponseEntity<PagedResult<UtilisateurModel>> donnerListeUtilisateurs(
        @RequestParam(name="page", defaultValue="1") int page,
        @RequestParam(name="per_page", defaultValue="10") int perPage
    ) {
        PagedResult<UtilisateurModel> result = gererUtilisateursUseCase.lister(page, perPage);

        return ResponseEntity.ok(result);
    }

    @Operation(summary="enregistre les utilisateurs")
    @Parameter(name="Authorization", in = ParameterIn.HEADER, required=true)
    @PostMapping()
    public ResponseEntity<UtilisateurModel> enregistrer(
        @RequestBody CreerUtilisateurCommand command  
    ) {

        UtilisateurModel model = gererUtilisateursUseCase.creerUtilisateur(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);

        //throw new UnsupportedOperationException("Method non implementée");
    }

    /* -------------------------------------------------------- */
    //
    // DTO
    //
    /* --------------------------------------------------------- */
     
    
}