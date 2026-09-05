package com.mich.ged.domain.interfaces.out;

import java.util.Optional;

import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.models.CommentaireModel;
import com.mich.ged.domain.models.DossierModel;

public interface CommentaireRepositoryPort {
    
    CommentaireModel save(CommentaireModel model);
    Optional<CommentaireModel> findById(Long id);
    PagedResult<CommentaireModel> findByDosier(DossierModel dossier, int page, int per_page);
}
