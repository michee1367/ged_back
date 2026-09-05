package com.mich.ged.adapters.out.persistence.entities;
import java.time.LocalDateTime;

import com.mich.ged.domain.models.StatutDossier;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "historique_transmissions")
public class HistoriqueTransmissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String motif;

    @Column(nullable = false)
    private LocalDateTime dateTransmission = LocalDateTime.now();

    // Rattaché à un dossier
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_id", nullable = false)
    private DossierEntity dossier;

    // Transmis par (Utilisateur - obligatoire)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expediteur_user_id", nullable = true)
    private UtilisateurEntity expediteurUser;

    // Transmis à (Utilisateur - obligatoire)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinataire_user_id", nullable = true)
    private UtilisateurEntity destinataireUser;

    // Service expéditeur (optionnel : 0..1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_expediteur_id")
    private ServiceEntity serviceExpediteur;

    // Service destinataire (optionnel : 0..1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_destinataire_id")
    private ServiceEntity serviceDestinataire;
    
    @Column(nullable = false)
    private String actionEffectuee;

    @Column(nullable = false)
    private String commentaire;
    
    @Enumerated(EnumType.STRING)
    private StatutDossier statutApresAction;


    public HistoriqueTransmissionEntity() {
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public LocalDateTime getDateTransmission() { return dateTransmission; }
    public void setDateTransmission(LocalDateTime dateTransmission) { this.dateTransmission = dateTransmission; }

    public DossierEntity getDossier() { return dossier; }
    public void setDossier(DossierEntity dossier) { this.dossier = dossier; }

    public UtilisateurEntity getExpediteurUser() { return expediteurUser; }
    public void setExpediteurUser(UtilisateurEntity expediteurUser) { this.expediteurUser = expediteurUser; }

    public UtilisateurEntity getDestinataireUser() { return destinataireUser; }
    public void setDestinataireUser(UtilisateurEntity destinataireUser) { this.destinataireUser = destinataireUser; }

    public ServiceEntity getServiceExpediteur() { return serviceExpediteur; }
    public void setServiceExpediteur(ServiceEntity serviceExpediteur) { this.serviceExpediteur = serviceExpediteur; }

    public ServiceEntity getServiceDestinataire() { return serviceDestinataire; }
    public void setServiceDestinataire(ServiceEntity serviceDestinataire) { this.serviceDestinataire = serviceDestinataire; }

    public String getActionEffectuee() {
        return actionEffectuee;
    }

    public void setActionEffectuee(String actionEffectuee) {
        this.actionEffectuee = actionEffectuee;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public StatutDossier getStatutApresAction() {
        return statutApresAction;
    }

    public void setStatutApresAction(StatutDossier statutApresAction) {
        this.statutApresAction = statutApresAction;
    }
}