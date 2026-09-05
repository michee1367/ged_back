package com.mich.ged.domain.interfaces.in;

import org.springframework.core.io.Resource;

import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.models.CommentaireModel;
import com.mich.ged.domain.models.DocumentModel;
import com.mich.ged.domain.models.DossierModel;
import com.mich.ged.domain.models.HistoriqueTransmissionModel;
import com.mich.ged.domain.models.ReponseModel;

public interface DossierUseCase {
    
    PagedResult<DossierModel> tous(int page, int perPage);
    PagedResult<DossierModel> tousParUtilisateur(Long utilisateurId, int page, int perPage);
    PagedResult<DossierModel> tousParUtilisateur(String nomUtilisateur, int page, int perPage);
    PagedResult<DocumentModel> tousDocuments(Long idDossier, int page, int perPage);
    PagedResult<HistoriqueTransmissionModel> tousHistorique(Long idDossier, int page, int perPage);
    
    PagedResult<CommentaireModel> tousCommentaire(Long idDossier, int page, int perPage);
    ReponseModel reponse(Long idDossier);
    
    DossierModel un(Long id);

    Resource loadDocumentAsResource(Long idDocument);
    
    DossierModel commenter(Long idDossier, String contenu);
    DossierModel repondre(Long idDossier, String contenu);

    /**
     * RechercheDossierQuery
     */
    public record DossierQuery(
        int page,
        int parPage
    ) {
    }


    
}
