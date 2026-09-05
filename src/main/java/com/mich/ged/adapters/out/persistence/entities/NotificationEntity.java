package com.mich.ged.adapters.out.persistence.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotification;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    private LocalDateTime dateEnvoi = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean lue = false;

    // 0..* Notification -> 1 Utilisateur
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinataire_id", nullable = false)
    private UtilisateurEntity destinataire;
    // 0..* Notification -> 1 Utilisateur
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_destinataire_id", nullable = false)
    private ServiceEntity serviceDestinataire;

    public NotificationEntity() {
    }

    // Getters et Setters
    public Long getIdNotification() { return idNotification; }
    public void setIdNotification(Long idNotification) { this.idNotification = idNotification; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getDateEnvoi() { return dateEnvoi; }
    public void setDateEnvoi(LocalDateTime dateEnvoi) { this.dateEnvoi = dateEnvoi; }

    public Boolean getLue() { return lue; }
    public void setLue(Boolean lue) { this.lue = lue; }

    public UtilisateurEntity getDestinataire() { return destinataire; }
    public void setDestinataire(UtilisateurEntity destinataire) { this.destinataire = destinataire; }

    public ServiceEntity getServiceDestinataire() {
        return serviceDestinataire;
    }

    public void setServiceDestinataire(ServiceEntity serviceDestinataire) {
        this.serviceDestinataire = serviceDestinataire;
    }
}