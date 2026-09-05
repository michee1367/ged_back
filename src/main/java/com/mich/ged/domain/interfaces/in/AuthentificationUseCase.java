package com.mich.ged.domain.interfaces.in;

public interface AuthentificationUseCase {
    ConnexionReponse seConnecter(ConnexionCommand command);

    /**
     * ConnexionCommand
     */
    public record ConnexionCommand(
        String username,
        String password
    ) {

    }

    /**
     * ConnexionReponse
     */
    public record ConnexionReponse(
        String accessToken,
        String type
    ) {
    }
}
