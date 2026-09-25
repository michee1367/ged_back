package com.mich.ged.adapters.in;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mich.ged.domain.interfaces.in.AuthentificationUseCase;
import com.mich.ged.domain.interfaces.in.AuthentificationUseCase.ConnexionCommand;
import com.mich.ged.domain.interfaces.in.AuthentificationUseCase.ConnexionReponse;
import com.mich.ged.domain.interfaces.in.GererUtilisateursUseCase;
import com.mich.ged.domain.interfaces.in.GererUtilisateursUseCase.CreerUtilisateurCommand;
import com.mich.ged.domain.models.TypeRole;
import com.mich.ged.domain.models.UtilisateurModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name="authentification", description="gerer l'authentification")
@RequestMapping("/auth")
@RestController
public class AuthentificationControleur {
    private GererUtilisateursUseCase gererUtilisateursUseCase;
    private AuthentificationUseCase authentificationUseCase;

    public AuthentificationControleur(
        GererUtilisateursUseCase gererUtilisateursUseCase,
        AuthentificationUseCase authentificationUseCase
    ) {
        this.gererUtilisateursUseCase = gererUtilisateursUseCase;
        this.authentificationUseCase = authentificationUseCase;
    }

    @Operation(summary="enregistrer utilisateur")
    @PostMapping("/enregistrer")
    public ResponseEntity<UtilisateurModel> enregistrer(
        @RequestBody CreerUtilisateurCommand command
    ) {

        System.out.println("#########################");
        System.out.println(command.roles());
        System.out.println(command.nom());
        System.out.println(command.email());
        System.out.println(command.motDePasse());
        System.out.println(command.postNom());
        System.out.println(command.idService());
        System.out.println("##########################");

        CreerUtilisateurCommand newCommand = new CreerUtilisateurCommand(
            command.idUtilisateur(),
            command.nom(),
            command.postNom(),
            command.prenom(),
            command.phoneNumber(),
            command.email(),
            command.motDePasse(),
            Set.of(TypeRole.VISIT),
            command.idService()
        );
        
        UtilisateurModel result = gererUtilisateursUseCase.creerUtilisateur(newCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary="se connecter")
    @PostMapping("/login")
    public ResponseEntity<ConnexionReponse> login(
        @RequestBody ConnexionCommand command
    ) {
        ConnexionReponse result = authentificationUseCase.seConnecter(command);

        return ResponseEntity.ok(result);
    }

    

    
}
