package com.mich.ged.adapters.out.persistence.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mich.ged.adapters.out.persistence.entities.DocumentEntity;
import com.mich.ged.adapters.out.persistence.mappers.GenericMapper;
import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.interfaces.out.DocumentRepositoryPort;
import com.mich.ged.domain.models.DocumentModel;
import com.mich.ged.domain.models.TypeRole;
import com.mich.ged.domain.models.UtilisateurModel;

@Component
public class DocumentPersistenceAdapter implements DocumentRepositoryPort {

    private final SpringDataDocumentRepository springDataRepository;
    private final GenericMapper mapper;

    public DocumentPersistenceAdapter(SpringDataDocumentRepository springDataRepository,
                                       GenericMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public DocumentModel save(DocumentModel document) {
        DocumentEntity entity = mapper.toEntity(document);
        DocumentEntity saved = springDataRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentModel> findById(Long idDocument) {
        return springDataRepository.findById(idDocument)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResult<DocumentModel> findByDossierId(Long idDossier, int page, int perPage) {
        Pageable pageable = PageRequest.of(page-1, perPage);
        Page<DocumentEntity> entityPage = springDataRepository.findByDossierId(idDossier, pageable);

        return mapper.withEntityPage(entityPage, mapper::toDomain);
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResult<DocumentModel> findByUser(UtilisateurModel utilisateur, int page, int perPage) {
        Pageable pageable = PageRequest.of(Math.max(page-1, 0), perPage);
        Page<DocumentEntity> entityPage = springDataRepository.findByUser(utilisateur.idUtilisateur(), pageable);
        return mapper.withEntityPage(entityPage, mapper::toDomain);

        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Transactional(readOnly = true)
    public long countParVisibilite(UtilisateurModel utilisateur) {
        if (utilisateur.service() == null) {
            return 0;
        }
        return springDataRepository.countDocumentsParVisibilite(
            utilisateur.idUtilisateur(),
            utilisateur.service().idService(),
            utilisateur.roles().contains(TypeRole.SECRETAIRE_BUREAU)
        );
    }
}