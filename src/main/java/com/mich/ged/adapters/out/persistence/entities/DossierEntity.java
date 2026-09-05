package com.mich.ged.adapters.out.persistence.entities;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.mich.ged.domain.models.Priorite;
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
@Table(name = "dossiers")
public class DossierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero;

    @Column(nullable = false)
    private String objet;
    
    @Column(nullable = false)
    private LocalDateTime dateReception;
    
    @Column(nullable = false)
    private LocalDate echeance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priorite priorite = Priorite.NORMALE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDossier statut = StatutDossier.RECU;

    // Utilisateur ayant enregistre le dossier
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_enregistreur_id", nullable = true)
    private UtilisateurEntity agentEnregistreur;

    // Service destinataire principal
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_destinataire_id")
    private ServiceEntity serviceDestinataire;

    // Composition 1..* avec DocumentEntity
    /*@OneToMany(mappedBy = "dossier", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<DocumentEntity> documents = new ArrayList<>();

    // Composition 0..* avec HistoriqueTransmissionEntity
    @OneToMany(mappedBy = "dossier", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<HistoriqueTransmissionEntity> historiques = new ArrayList<>();*/

    public DossierEntity() {
    }

    // Methodes d'aide pour maintenir la coherence bidirectionnelle (Composition)
    /*public void addDocument(DocumentEntity document) {
        documents.add(document);
        document.setDossier(this);
    }

    public void removeDocument(DocumentEntity document) {
        documents.remove(document);
        document.setDossier(null);
    }*/

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getObjet() { return objet; }
    public void setObjet(String objet) { this.objet = objet; }

    public UtilisateurEntity getAgentEnregistreur() { return agentEnregistreur; }
    public void setAgentEnregistreur(UtilisateurEntity agentEnregistreur) { this.agentEnregistreur = agentEnregistreur; }

    public ServiceEntity getServiceDestinataire() { return serviceDestinataire; }
    public void setServiceDestinataire(ServiceEntity serviceDestinataire) { this.serviceDestinataire = serviceDestinataire; }

    /*public List<DocumentEntity> getDocuments() { return documents; }
    public void setDocuments(List<DocumentEntity> documents) { this.documents = documents; }*/

    /*public List<HistoriqueTransmissionEntity> getHistoriques() { return historiques; }
    public void setHistoriques(List<HistoriqueTransmissionEntity> historiques) { this.historiques = historiques; }*/

    public Priorite getPriorite() {
        return priorite;
    }

    public void setPriorite(Priorite priorite) {
        this.priorite = priorite;
    }

    public StatutDossier getStatut() {
        return statut;
    }

    public void setStatut(StatutDossier statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateReception() {
        return dateReception;
    }

    public void setDateReception(LocalDateTime dateReception) {
        this.dateReception = dateReception;
    }

    public LocalDate getEcheance() {
        return echeance;
    }

    public void setEcheance(LocalDate echeance) {
        this.echeance = echeance;
    }
}