package com.mich.ged.domain.models;
import java.time.LocalDateTime;

public record NotificationModel(
    Long idNotification,
    UtilisateurModel utilisateur,
    ServiceModel service,
    String message,
    LocalDateTime dateEnvoi,
    Boolean lue

) {
    
}
