package com.mich.ged.application;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.interfaces.in.DossierUseCase;
import com.mich.ged.domain.interfaces.in.EnregistrerDossierUseCase;
import com.mich.ged.domain.interfaces.out.CommentaireRepositoryPort;
import com.mich.ged.domain.interfaces.out.DocumentRepositoryPort;
import com.mich.ged.domain.interfaces.out.DocumentStoragePort;
import com.mich.ged.domain.interfaces.out.DossierRepositoryPort;
import com.mich.ged.domain.interfaces.out.HistoriqueRepositoryPort;
import com.mich.ged.domain.interfaces.out.NotificationPort;
import com.mich.ged.domain.interfaces.out.ReponseRepositoryPort;
import com.mich.ged.domain.interfaces.out.UtilisateurRepositoryPort;
import com.mich.ged.domain.models.CommentaireModel;
import com.mich.ged.domain.models.DocumentModel;
import com.mich.ged.domain.models.DossierModel;
import com.mich.ged.domain.models.HistoriqueTransmissionModel;
import com.mich.ged.domain.models.ReponseModel;
import com.mich.ged.domain.models.StatutDossier;
import com.mich.ged.domain.models.UtilisateurModel;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DossierService implements EnregistrerDossierUseCase, DossierUseCase {

    private final DossierRepositoryPort dossierRepo;
    private final HistoriqueRepositoryPort historiqueRepo;
    private final DocumentStoragePort storagePort;
    private final NotificationPort notificationPort;
    private final DocumentRepositoryPort documentRepo;
    private final UtilisateurRepositoryPort utilisateurRepositoryPort;
    private final CommentaireRepositoryPort commentaireRepo;
    private final ReponseRepositoryPort reponseRepo;

    public DossierService(DossierRepositoryPort dossierRepo,
                                     HistoriqueRepositoryPort historiqueRepo,
                                     UtilisateurRepositoryPort utilisateurRepositoryPort,
                                     DocumentStoragePort storagePort,
                                     DocumentRepositoryPort documentRepo,
                                     CommentaireRepositoryPort commentaireRepo,
                                     ReponseRepositoryPort reponseRepo,
                                     NotificationPort notificationPort) {

        this.dossierRepo = dossierRepo;
        this.documentRepo = documentRepo;
        this.historiqueRepo = historiqueRepo;
        this.storagePort = storagePort;
        this.notificationPort = notificationPort;
        this.utilisateurRepositoryPort = utilisateurRepositoryPort;
        this.commentaireRepo = commentaireRepo;
        this.reponseRepo = reponseRepo;
    
    }

    @Override
    public DossierModel enregistrer(EnregistrerDossierCommand command) {
        //throw new UnsupportedOperationException("Not supported yet.");
        UtilisateurModel utilisateur = utilisateurRepositoryPort.findByUsername(command.nomUtilisateur()).orElseThrow(
            () -> new EntityNotFoundException("Utilisateur n'existe pas")
        );
        // 2. Génération du numéro de dossier unique
        String numeroUnique = "DOS-" + LocalDateTime.now().getYear() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // 3. Instanciation du dossier
        
        DossierModel nouveauDossier = new DossierModel(
            null,
            numeroUnique,
            command.objet(),
            LocalDateTime.now(),
            command.echeance(),
            command.priorite(),
            StatutDossier.RECU,
            utilisateur,
            utilisateur.service(),
            List.of(),
            List.of()
        );

       DossierModel dossierSauve = dossierRepo.save(nouveauDossier);

        // 4. Trace initiale dans l'historique
        HistoriqueTransmissionModel traceInitiale = new HistoriqueTransmissionModel(
            null,
            dossierSauve,
            LocalDateTime.now(),
            null,
            utilisateur,
            null,
            utilisateur.service(),
            "Réception et enregistrement du dossier",
            "Dossier créé",
            StatutDossier.RECU
        );

        historiqueRepo.save(traceInitiale);

        return dossierSauve;
    }

    @Override
    public DocumentModel joindreDocument(JoindreDocumentCommand command) {
        
        DossierModel dossier = dossierRepo.findById(command.idDossier()).orElseThrow(() -> new UnsupportedOperationException("Not supported yet."));

        String cheminDoc = storagePort.stockerFichier(command.nom(), command.contenu());

        DocumentModel document = new DocumentModel(
            null,
            command.titre(),
            cheminDoc,
            LocalDateTime.now(),
            null,
            dossier
        );

        DocumentModel documentSauve = documentRepo.save(document);

        return documentSauve;

    }

    @Override
    public PagedResult<DossierModel> tous(int page, int perPage) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return dossierRepo.findAll(page, perPage);
    }

    @Override
    public DossierModel un(Long id) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return dossierRepo.findById(id).orElseThrow(() -> new UnsupportedOperationException("Not supported yet."));

        //ReponseModel reponse = reponseRepo.findOne();
    }

    @Override
    public PagedResult<DocumentModel> tousDocuments(Long idDossier, int page, int perPage) {
        return documentRepo.findByDossierId(idDossier, page, perPage);
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PagedResult<HistoriqueTransmissionModel> tousHistorique(Long idDossier, int page, int perPage) {
        return historiqueRepo.findByDossierId(idDossier, page, perPage);
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Resource loadDocumentAsResource(Long idDocument) {
        DocumentModel document = documentRepo.findById(idDocument)
                .orElseThrow(() -> new EntityNotFoundException("Document non trouvé"));

        try {
            Path path = Paths.get(document.cheminStockage());
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Impossible de lire le fichier à l'emplacement indiqué.");
            }
        } catch (RuntimeException | MalformedURLException e) {
            throw new RuntimeException("Erreur lors du chargement du fichier", e);
        }
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PagedResult<CommentaireModel> tousCommentaire(Long idDossier, int page, int perPage) {
        DossierModel dossier = dossierRepo.findById(idDossier).orElseThrow(() -> new UnsupportedOperationException("Not supported yet."));
        return commentaireRepo.findByDosier(dossier, page, perPage);
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ReponseModel reponse(Long idDossier) {
        
        DossierModel dossier = dossierRepo.findById(
            idDossier
        ).orElseThrow(() -> new EntityNotFoundException("Dossier Existe pas"));

        ReponseModel reponse = reponseRepo.findByDossier(dossier).orElseThrow(
            () -> new EntityNotFoundException("Pas de reponse")
        );

        return reponse;
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DossierModel commenter(Long idDossier, String contenu) {
        DossierModel dossier = dossierRepo.findById(
            idDossier
        ).orElseThrow(() -> new EntityNotFoundException("Dossier Existe pas"));
        
        CommentaireModel commentaire = new CommentaireModel(
            null,
            contenu,
            dossier,
            null
        );

        CommentaireModel newCommentaire = commentaireRepo.save(commentaire);
        return newCommentaire.dossier();

        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DossierModel repondre(Long idDossier, String contenu) {
        DossierModel dossier = dossierRepo.findById(
            idDossier
        ).orElseThrow(() -> new EntityNotFoundException("Dossier Existe pas"));
        
        ReponseModel reponse = reponseRepo.findByDossier(dossier).orElseGet(
            () -> {

                ReponseModel reponseModel = new ReponseModel(
                    null,
                    contenu,
                    dossier
                );
                return reponseModel;
            } 
        
        );

        ReponseModel newReponse =  reponse.changeConetenu(contenu);

        ReponseModel reponseSave = reponseRepo.save(newReponse);

        return reponseSave.dossier();
        
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PagedResult<DossierModel> tousParUtilisateur(Long utilisateurId, int page, int perPage) {
        UtilisateurModel utilisateur = utilisateurRepositoryPort.findById(utilisateurId).orElseThrow(() -> new EntityNotFoundException("Utilisateur n'existe pas"));
        return dossierRepo.findByUser(utilisateur, page, perPage);
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PagedResult<DossierModel> tousParUtilisateur(String nomUtilisateur, int page, int perPage) {
        UtilisateurModel utilisateur = utilisateurRepositoryPort.findByUsername(nomUtilisateur).orElseThrow(() -> new EntityNotFoundException("Utilisateur n'existe pas"));
        return dossierRepo.findByUser(utilisateur, page, perPage);
        
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
