package com.mich.ged.application;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mich.ged.domain.interfaces.in.AuthentificationUseCase;
import com.mich.ged.security.JwtUtils;

@Service
public class AuthentificationService implements AuthentificationUseCase {
    private AuthenticationManager authenticationManager;

    private JwtUtils jwtUtils;
    // Injection par constructeur (recommandé)
    public AuthentificationService(
        AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ConnexionReponse seConnecter(ConnexionCommand command) {
        System.out.println("#####################");
        System.out.println(command.username());
        System.out.println(command.password());
        System.out.println("#####################");
        
        var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(command.username(), command.password())
        );
        // 2. Récupérer l'utilisateur authentifié
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 3. Générer le Token JWT en lui passant l'utilisateur (avec ses rôles)
        String token = jwtUtils.generateToken(userDetails);

        // 4. Retourner le DTO de réponse métier
        return new ConnexionReponse(token, "Bearer");

        //String token = jwtUtils;

        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
