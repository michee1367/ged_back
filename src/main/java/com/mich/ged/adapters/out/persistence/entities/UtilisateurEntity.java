package com.mich.ged.adapters.out.persistence.entities;

import java.util.Set;

import com.mich.ged.domain.models.TypeRole;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
@Table(name = "utilisateurs")
public class UtilisateurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtilisateur;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = true)
    private String postNom;

    @Column(nullable = true)
    private String prenom;

    @Column(nullable = true, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = true)
    private String motDePasse;

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(
        name="utilisateur_roles",
        joinColumns=@JoinColumn(name="utilisateur_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable=false)
    Set<TypeRole> roles;
    
    @Column(nullable = false)
    private Boolean actif = true;

    // Service auquel appartient l'utilisateur
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    // 1 Utilisateur -> 0..* Notifications
    /*@OneToMany(mappedBy = "destinataire", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationEntity> notifications = new ArrayList<>();

    // 1 Utilisateur -> 0..* Dossiers enregistrés
    @OneToMany(mappedBy = "agentEnregistreur")
    private List<DossierEntity> dossiersEnregistres = new ArrayList<>();*/

    public UtilisateurEntity() {
    }

    // Getters et Setters
    public Long getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(Long idUtilisateur) { this.idUtilisateur = idUtilisateur; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public Boolean getActif() { return actif; }
    public void setActif(Boolean actif) { this.actif = actif; }

    public ServiceEntity getService() { return service; }
    public void setService(ServiceEntity service) { this.service = service; }
    /* 
    public List<NotificationEntity> getNotifications() { return notifications; }
    public void setNotifications(List<NotificationEntity> notifications) { this.notifications = notifications; }

    public List<DossierEntity> getDossiersEnregistres() { return dossiersEnregistres; }
    public void setDossiersEnregistres(List<DossierEntity> dossiersEnregistres) { this.dossiersEnregistres = dossiersEnregistres; }
    */
    public String getPostNom() {
        return postNom;
    }

    public void setPostNom(String postNom) {
        this.postNom = postNom;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<TypeRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<TypeRole> roles) {
        this.roles = roles;
    }
}