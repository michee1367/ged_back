package com.mich.ged.domain.interfaces.out;
import java.util.Optional;

import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.models.DocumentModel;
import com.mich.ged.domain.models.UtilisateurModel;

public interface DocumentRepositoryPort {
    DocumentModel save(DocumentModel document);
    Optional<DocumentModel> findById(Long idDocument);    
    PagedResult<DocumentModel> findByDossierId(Long idDossier, int page, int perPage);
    PagedResult<DocumentModel> findByUser(UtilisateurModel utilisateur, int page, int perPage);
    long countParVisibilite(UtilisateurModel utilisateur);

    
}
