package com.mich.ged.adapters.out.persistence.entities;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "services")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idService;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    // 1 Service -> 0..* Utilisateurs
    @OneToMany(mappedBy = "service")
    private List<UtilisateurEntity> utilisateurs = new ArrayList<>();

    // 1 Service -> 0..* Dossiers (destinataire principal)
    /*@OneToMany(mappedBy = "serviceDestinataire")
    private List<DossierEntity> dossiersRecus = new ArrayList<>();*/

    public ServiceEntity() {
    }

    public ServiceEntity(String nom, String code) {
        this.nom = nom;
        this.code = code;
    }

    // Getters et Setters
    public Long getIdService() { return idService; }
    public void setIdService(Long idService) { this.idService = idService; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public List<UtilisateurEntity> getUtilisateurs() { return utilisateurs; }
    public void setUtilisateurs(List<UtilisateurEntity> utilisateurs) { this.utilisateurs = utilisateurs; }
    /* 
    public List<DossierEntity> getDossiersRecus() { return dossiersRecus; }
    public void setDossiersRecus(List<DossierEntity> dossiersRecus) { this.dossiersRecus = dossiersRecus; }*/
}
