package com.mich.ged.adapters.out.persistence.mappers;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.mich.ged.adapters.out.persistence.entities.CommentaireEntity;
import com.mich.ged.adapters.out.persistence.entities.DocumentEntity;
import com.mich.ged.adapters.out.persistence.entities.DossierEntity;
import com.mich.ged.adapters.out.persistence.entities.HistoriqueTransmissionEntity;
import com.mich.ged.adapters.out.persistence.entities.NotificationEntity;
import com.mich.ged.adapters.out.persistence.entities.ReponseEntity;
import com.mich.ged.adapters.out.persistence.entities.ServiceEntity;
import com.mich.ged.adapters.out.persistence.entities.UtilisateurEntity;
import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.models.CommentaireModel;
import com.mich.ged.domain.models.DocumentModel;
import com.mich.ged.domain.models.DossierModel;
import com.mich.ged.domain.models.HistoriqueTransmissionModel;
import com.mich.ged.domain.models.NotificationModel;
import com.mich.ged.domain.models.ReponseModel;
import com.mich.ged.domain.models.ServiceModel;
import com.mich.ged.domain.models.UtilisateurModel;

@Component
public class GenericMapper {

    // ==========================================
    // DOCUMENT
    // ==========================================
    public DocumentModel toDomain(DocumentEntity entity) {
        if (entity == null) return null;

        DocumentModel domain = new DocumentModel(
            entity.getId(),
            entity.getTitre(),
            entity.getCheminFichier(),
            entity.getDateAjout(),
            null,
            toDomain(entity.getDossier())
        );
        return domain;
    }

    public DocumentEntity toEntity(DocumentModel domain) {
        if (domain == null) return null;

        DocumentEntity entity = new DocumentEntity();
        entity.setId(domain.idDocument());
        entity.setTitre(domain.nomFichier());
        entity.setCheminFichier(domain.cheminStockage());
        entity.setDateAjout(domain.dateAjout());
        entity.setDossier(toEntity(domain.dossier()));
        //entity.setTaille(domain.getTaille());
        return entity;
    }

    // ==========================================
    // SERVICE
    // ==========================================
    public ServiceModel toDomain(ServiceEntity entity) {
        if (entity == null) return null;

        ServiceModel domain = new ServiceModel(
            entity.getIdService(),
            entity.getNom(),
            entity.getCode()
        );
        return domain;
    }

    public ServiceEntity toEntity(ServiceModel domain) {
        if (domain == null) return null;

        ServiceEntity entity = new ServiceEntity();
        entity.setIdService(domain.idService());
        entity.setNom(domain.nom());
        entity.setCode(domain.code());

        return entity;
    }
    public ServiceEntity toEntity(ServiceModel domain, ServiceEntity entity) {
        if (domain == null) return null;

        if (entity == null) {
            entity = new ServiceEntity();
        }
        
        entity.setIdService(domain.idService());
        entity.setNom(domain.nom());
        entity.setCode(domain.code());

        return entity;
    }

    // ==========================================
    // UTILISATEUR
    // ==========================================
    public UtilisateurModel toDomain(UtilisateurEntity entity) {
        if (entity == null) return null;

        UtilisateurModel domain = new UtilisateurModel(
            entity.getIdUtilisateur(),
            entity.getNom(),
            entity.getPostNom(),
            entity.getPrenom(),
            entity.getPhoneNumber(),
            entity.getEmail(),
            entity.getMotDePasse(),
            entity.getRoles(),
            entity.getActif(),
            toDomain(entity.getService())
        );

        return domain;
    }

    public UtilisateurEntity toEntity(UtilisateurModel domain) {
        
        if (domain == null) return null;

        UtilisateurEntity entity = new UtilisateurEntity();

        entity.setIdUtilisateur(domain.idUtilisateur());
        entity.setNom(domain.nom());
        entity.setPrenom(domain.prenom());
        entity.setPostNom(domain.postNom());
        entity.setEmail(domain.email());
        entity.setPhoneNumber(domain.phoneNumber());
        entity.setMotDePasse(domain.motDePasse());
        entity.setRoles(domain.roles());
        entity.setActif(domain.actif());
        entity.setService(toEntity(domain.service()));

        return entity;
        
    }

    public UtilisateurEntity toEntity(UtilisateurModel domain, UtilisateurEntity entity) {
        if (domain == null) return null;

        if (entity == null) {
            entity = new UtilisateurEntity();
        }

        entity.setIdUtilisateur(domain.idUtilisateur());
        entity.setNom(domain.nom());
        entity.setPrenom(domain.prenom());
        entity.setPostNom(domain.postNom());
        entity.setEmail(domain.email());
        entity.setPhoneNumber(domain.phoneNumber());
        entity.setMotDePasse(domain.motDePasse());
        entity.setRoles(domain.roles());
        entity.setActif(domain.actif());
        entity.setService(toEntity(domain.service()));

        return entity;
    }

    // ==========================================
    // HISTORIQUE TRANSMISSION
    // ==========================================
    public HistoriqueTransmissionModel toDomain(HistoriqueTransmissionEntity entity) {
        if (entity == null) return null;

        HistoriqueTransmissionModel domain = new HistoriqueTransmissionModel(
            entity.getId(),
            toDomain(entity.getDossier()),
            entity.getDateTransmission(),
            toDomain(entity.getExpediteurUser()),
            toDomain(entity.getDestinataireUser()),
            toDomain(entity.getServiceExpediteur()),
            toDomain(entity.getServiceDestinataire()),
            entity.getActionEffectuee(),
            entity.getCommentaire(),
            entity.getStatutApresAction()
        );

        return domain;
    }

    public HistoriqueTransmissionEntity toEntity(HistoriqueTransmissionModel domain) {
        if (domain == null) return null;

        HistoriqueTransmissionEntity entity = new HistoriqueTransmissionEntity();
        
        entity.setId(domain.idHistorique());
        entity.setDateTransmission(domain.dateHeureTransmission());
        entity.setCommentaire(domain.commentaire());
        entity.setDossier(toEntity(domain.dossier()));
        entity.setExpediteurUser(toEntity(domain.expediteur()));
        entity.setDestinataireUser(toEntity(domain.destinataire()));
        entity.setServiceExpediteur(toEntity(domain.serviceExpediteur()));
        entity.setServiceDestinataire(toEntity(domain.serviceDestinataire()));
        entity.setActionEffectuee(domain.actionEffectuee());
        entity.setStatutApresAction(domain.statutApresAction());

        return entity;
    }

    // ==========================================
    // NOTIFICATION
    // ==========================================
    public NotificationModel toDomain(NotificationEntity entity) {
        if (entity == null) return null;

        NotificationModel domain = new NotificationModel(
            entity.getIdNotification(),
            toDomain(entity.getDestinataire()),
            toDomain(entity.getServiceDestinataire()),
            entity.getMessage(),
            entity.getDateEnvoi(),
            entity.getLue()
        );
        return domain;
    }

    public NotificationEntity toEntity(NotificationModel domain) {
        if (domain == null) return null;

        NotificationEntity entity = new NotificationEntity();
        entity.setIdNotification(domain.idNotification());
        entity.setMessage(domain.message());
        entity.setLue(domain.lue());
        entity.setDateEnvoi(domain.dateEnvoi());
        entity.setDestinataire(toEntity(domain.utilisateur()));
        entity.setServiceDestinataire(toEntity(domain.service()));
        return entity;
    }

    // ==========================================
    // DOSSIER
    // ==========================================
    public DossierModel toDomain(DossierEntity entity) {
        if (entity == null) return null;

        DossierModel domain = new DossierModel(
            entity.getId(),
            entity.getNumero(),
            entity.getObjet(),
            entity.getDateReception(),
            entity.getEcheance(),
            entity.getPriorite(),
            entity.getStatut(),
            toDomain(entity.getAgentEnregistreur()),
            toDomain(entity.getServiceDestinataire()),
            null,
            null
            /*entity.getDocuments() != null? entity.getDocuments().stream()
                    .map(this::toDomain)
                    .collect(Collectors.toList()) : null,
            entity.getHistoriques() != null ? entity.getHistoriques().stream()
                    .map(this::toDomain)
                    .collect(Collectors.toList()) : null*/
        );

        return domain;
    }

    public DossierEntity toEntity(DossierModel domain) {
        if (domain == null) return null;

        DossierEntity entity = new DossierEntity();
        entity.setId(domain.idDossier());
        entity.setNumero(domain.numeroDossier());
        entity.setObjet(domain.objet());
        entity.setEcheance(domain.echeance());
        entity.setDateReception(domain.dateReception());
        entity.setPriorite(domain.priorite());
        entity.setStatut(domain.statutActuel());
        entity.setAgentEnregistreur(toEntity(domain.agentResponsable()));
        entity.setServiceDestinataire(toEntity(domain.serviceActuel()));

        /*if (domain.documents() != null) {
            domain.documents().forEach(docModel -> 
                entity.addDocument(toEntity(docModel))
            );
        }

        if (domain.historiques() != null) {
            domain.historiques().forEach(hModel -> {
                HistoriqueTransmissionEntity hEntity = toEntity(hModel);
                hEntity.setDossier(entity);
                entity.getHistoriques().add(hEntity);
            });
        }*/

        return entity;
    }
    public DossierEntity toEntity(DossierModel domain, DossierEntity entity) {
        if (domain == null) return null;

        entity.setId(domain.idDossier());
        entity.setNumero(domain.numeroDossier());
        entity.setObjet(domain.objet());
        entity.setEcheance(domain.echeance());
        entity.setDateReception(domain.dateReception());
        entity.setPriorite(domain.priorite());
        entity.setStatut(domain.statutActuel());
        entity.setAgentEnregistreur(toEntity(domain.agentResponsable()));
        entity.setServiceDestinataire(toEntity(domain.serviceActuel()));

        return entity;
    }

    public ReponseModel toDomain(ReponseEntity entity) {

        ReponseModel model = new ReponseModel(
            entity.getId(), 
            entity.getContenu(), 
            toDomain(entity.getDossier())
        );

        return model;
        
    }

    public ReponseEntity toEntity(ReponseModel model) {
        ReponseEntity entity = new ReponseEntity();

        entity.setId(model.idReponse());
        entity.setContenu(model.contenu());
        
        entity.setDossier(
            this.toEntity(model.dossier())
        );
        
        return entity;

    }

    public CommentaireModel toDomain(CommentaireEntity entity) {

        CommentaireModel newModel = new CommentaireModel(
            entity.getId(),
            entity.getContenu(),
            this.toDomain(entity.getDossier()),
            this.toDomain(entity.getContributeur())
        );

        return newModel;
    }

    public CommentaireEntity toEntity(CommentaireModel model) {
        
        CommentaireEntity entity = new CommentaireEntity();
        entity.setContenu(model.contenu());
        entity.setDossier(this.toEntity(model.dossier()));

        entity.setContributeur(
            this.toEntity(model.contributeur())
        );

        return entity;

    }

    public <E, D> PagedResult<D> withEntityPage(Page<E> entityPage, Function<E, D> mapper) {
        
        List<D> modelsList = entityPage.stream().map(
            mapper
        ).toList();

        PagedResult<D> result = new PagedResult<>(
            modelsList,
            entityPage.getNumber()+1,
            entityPage.getSize(),
            entityPage.getTotalElements(),
            entityPage.getTotalPages(),
            entityPage.isLast()
        );

        return result;
    }
    
}