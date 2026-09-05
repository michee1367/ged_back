package com.mich.ged.adapters.in;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mich.ged.domain.dto.PagedResult;
import com.mich.ged.domain.interfaces.in.DossierUseCase;
import com.mich.ged.domain.interfaces.in.EnregistrerDossierUseCase;
import com.mich.ged.domain.interfaces.in.EnregistrerDossierUseCase.EnregistrerDossierCommand;
import com.mich.ged.domain.interfaces.in.EnregistrerDossierUseCase.JoindreDocumentCommand;
import com.mich.ged.domain.interfaces.in.TransmettreDossierUseCase;
import com.mich.ged.domain.interfaces.in.TransmettreDossierUseCase.TransmettreDossierCommand;
import com.mich.ged.domain.models.CommentaireModel;
import com.mich.ged.domain.models.DocumentModel;
import com.mich.ged.domain.models.DossierModel;
import com.mich.ged.domain.models.HistoriqueTransmissionModel;
import com.mich.ged.domain.models.Priorite;
import com.mich.ged.domain.models.ReponseModel;
import com.mich.ged.domain.models.StatutDossier;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="Dossiers", description="Gestion des dossiers")
@RestController
@RequestMapping("/dossiers")
public class DossierController {

    private final EnregistrerDossierUseCase enregistrerDossierUseCase;
    private final TransmettreDossierUseCase transmettreDossierUseCase;
    private final DossierUseCase dossierUseCase;

    public DossierController(
        EnregistrerDossierUseCase enregistrerDossierUseCase, 
        TransmettreDossierUseCase transmettreDossierUseCase,
        DossierUseCase dossierUseCase
    ) {
        this.enregistrerDossierUseCase = enregistrerDossierUseCase;
        this.transmettreDossierUseCase = transmettreDossierUseCase;
        this.dossierUseCase = dossierUseCase;
    }

    @PostMapping
    @Operation(summary="Enregistre un dossier")
    public ResponseEntity<ReponseDossier> enregistrer(
        @RequestBody EnregistrerDossierCommand command,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        EnregistrerDossierCommand newCommand = new EnregistrerDossierCommand(
            command.objet(),
            command.expediteurOrigineId(),
            command.echeance(),
            command.priorite(),
            userDetails.getUsername()
        );

        DossierModel response = enregistrerDossierUseCase.enregistrer(newCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ReponseDossier(response));
    }

    /**
     * Attache un fichier unique à un dossier
     */
    @PostMapping(path="/{dossier_id}/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary="Joindre un document à dossier")
    public ResponseEntity<ReponseDossier> joindreFichier(
            @PathVariable("dossier_id") Long dossierId,
            @RequestParam("titre") String titre,
            @RequestParam("fichier") MultipartFile fichier
    ) {
        try {
        JoindreDocumentCommand command = new JoindreDocumentCommand(
            fichier.getOriginalFilename(), titre, fichier.getBytes(), dossierId
        );
        DocumentModel documentCree = enregistrerDossierUseCase.joindreDocument(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ReponseDossier(documentCree.dossier()));
            
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du traitement du fichier : " + e.getMessage(), e);
        }
    }
    /**
     * lister les fichiers d'un dossier
     */
    @GetMapping(path="/{dossier_id}/documents")
    @Operation(summary="Joindre un document à dossier")
    public ResponseEntity<PagedResult<ReponseDocument>> tousDocuments(
            @PathVariable("dossier_id") Long dossierId,
            @RequestParam("page") int page,
            @RequestParam("per_page") int perPage
    ) {
        PagedResult<DocumentModel> result = dossierUseCase.tousDocuments(dossierId, page, perPage);
        
        PagedResult<ReponseDocument> response = new PagedResult<>(
            result.content().stream().map(this::toDto).toList(), 
            result.page(),
            result.size(),
            result.totalElements(), 
            result.totalPages(), 
            result.isLast()
        );

        return ResponseEntity.ok(response);
    }

    /**
     * lister les fichiers d'un dossier
     */
    @GetMapping(path="/documents/documents/{document_id}/download")
    @Operation(summary="Joindre un document à dossier")
    public ResponseEntity<Resource> telechargerDocument(
            @PathVariable("document_id") Long documentId
    ) {
        Resource result = dossierUseCase.loadDocumentAsResource(documentId);
        MediaType contentType = MediaTypeFactory.getMediaType(result).orElse(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + result.getFilename() + "\"")
                .contentType(contentType)
                .body(result);
    }

    @PostMapping("/{id}/transmettre")
    @Operation(summary="Transmet un dossier à un service ou agent")
    public ResponseEntity<ReponseDossier> transmettre(
        @PathVariable Long id,
        @RequestBody TransmettreDossierCommand command,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        // Garantir la cohérence entre le Path Variable et le Command Body
        TransmettreDossierCommand safeCommand = command.withId(id)
                                    .withUserName(
                                        userDetails.getUsername()
                                    );

        DossierModel response = transmettreDossierUseCase.transmettre(safeCommand);
        
        return ResponseEntity.ok(new ReponseDossier(response));
    }
    
    @GetMapping("/{id}/historiques")
    @Operation(summary="Transmet un dossier à un service ou agent")
    public ResponseEntity<PagedResult<HistoriqueTransmissionModel>> donnerHistorique(@PathVariable Long id,
        @RequestParam(name="page", defaultValue="1") int page,
        @RequestParam(name="per_page", defaultValue="10") int perPage) 
    {
        // Garantir la cohérence entre le Path Variable et le Command Body
        
        PagedResult<HistoriqueTransmissionModel> resultat = dossierUseCase.tousHistorique(id, page, perPage);
        return ResponseEntity.ok(resultat);
    }
    
    @GetMapping("/")
    @Operation(summary="Fourni la liste des dossiers")
    public ResponseEntity<PagedResult<ReponseDossier>> donnerTous(
        @AuthenticationPrincipal UserDetails utilisateur,
        @RequestParam(name="page", defaultValue="1") int page,
        @RequestParam(name="per_page", defaultValue="10") int perPage
    ) {

        PagedResult<DossierModel> dossiers = dossierUseCase.tousParUtilisateur(utilisateur.getUsername(), page, perPage);
        
        return ResponseEntity.ok(
            new PagedResult<>(
                dossiers.content().stream().map(
                    (DossierModel dossier) -> new ReponseDossier(dossier)
                ).toList(),
                dossiers.page(),
                dossiers.size(),
                dossiers.totalElements(),
                dossiers.totalPages(),
                dossiers.isLast()
            )
        );
        
    }



    /*------------------------------*/
    //
    // PRIVATE FONTION
    //
    /*------------------------------- */

    private ReponseDocument toDto(DocumentModel document) {
        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/dossiers/documents/")
                .path(document.idDocument().toString())
                .path("/download")
                .toUriString();

        ReponseDocument dto = new ReponseDocument(
            document.idDocument(),
            document.nomFichier(),
            fileUrl,
            document.dateAjout(),
            document.format(),
            document.dossier().idDossier(),
            document.dossier().objet(),
            document.dossier().dateReception()
        );

        return dto;
    }
    
    @GetMapping("/{id}")
    @Operation(summary="Fourni un dossier")
    public ResponseEntity<ReponseDossier> donnerUn(@PathVariable Long id) {

        DossierModel dossier = dossierUseCase.un(id);

        return ResponseEntity.ok(new ReponseDossier(dossier));

    }

    @PostMapping("/{idDossier}/commenter")
    @Operation(summary="commenter un dossier")
    public ResponseEntity<ReponseDossier> commenter(
        @PathVariable(name="idDossier") Long idDossier,
        @RequestBody() CommenterDto data
    ){
        DossierModel dossier = dossierUseCase.commenter(idDossier, data.contenu);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ReponseDossier(dossier));
    }
    @GetMapping("/{idDossier}/commentaires")
    @Operation(summary="commenter un dossier")
    public ResponseEntity<PagedResult<CommentaireModel>> commentaires(
        @PathVariable(name="idDossier") Long idDossier,
        @RequestParam(name="page", defaultValue="1") int page,
        @RequestParam(name="per_page", defaultValue="10") int perPage
    ){
        PagedResult<CommentaireModel> listes = dossierUseCase.tousCommentaire(idDossier, page, perPage);
        return ResponseEntity.ok(listes);
    }
    
    @PostMapping("{idDossier}/repondre") 
    @Operation(summary="repondre à un dossier")
    public ResponseEntity<ReponseDossier> repondre(
        @PathVariable(name="idDossier") Long idDossier,
        @RequestBody() RepondreDto data
    ){
        DossierModel dossier = dossierUseCase.repondre(idDossier, data.contenu);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ReponseDossier(dossier));

    }
    @GetMapping("/{idDossier}/reponses")
    @Operation(summary="commenter un dossier")
    public ResponseEntity<ReponseModel> reponses(
        @PathVariable(name="idDossier") Long idDossier
    ){
        ReponseModel reponse = dossierUseCase.reponse(idDossier);
        return ResponseEntity.ok(reponse);
    }

    

    /*------------------------------*/
    //
    // DTO
    //
    /*------------------------------- */

    /**
     * CommenterDto
     */
    public record CommenterDto(
        String contenu
    ) {
    }
    /**
     * RepondreDto
     */
    public record RepondreDto(
        String contenu
    ) {
    }

    /**
     * ReponseDossier
     */
    public record ReponseDossier(
        Long idDossier,
        String numeroDossier,
        String objet,
        LocalDateTime dateReception,
        LocalDate echeance,
        Priorite priorite,
        StatutDossier statutActuel,
        Long idAgent,
        String nomsAgent,
        Long idService,
        String nomService
    ) {
        public ReponseDossier(DossierModel dossierModel) {
            this(
                dossierModel.idDossier(),
                dossierModel.numeroDossier(),
                dossierModel.objet(),
                dossierModel.dateReception(),
                dossierModel.echeance(),
                dossierModel.priorite(),
                dossierModel.statutActuel(),
                dossierModel.agentResponsable() == null ? null : dossierModel.agentResponsable().idUtilisateur(),
                dossierModel.agentResponsable() == null ? null : dossierModel.agentResponsable().nomComplet(),
                dossierModel.serviceActuel() == null ? null : dossierModel.serviceActuel().idService(),
                dossierModel.serviceActuel() == null ? null : dossierModel.serviceActuel().nom()
            );
        }
    }

    /**
     * ReponseDocument
     */
    public record ReponseDocument(
        Long idDocument,
        String nomFichier,
        String url,
        LocalDateTime dateAjout,
        String format,
        Long idDossier,
        String objetDossier,
        LocalDateTime dateReception
    ) {
        public ReponseDocument(
        Long idDocument,
        String nomFichier,
        String url,
        LocalDateTime dateAjout,
        String format,
        DossierModel dossier) {
            this(
                idDocument, nomFichier, url, 
                dateAjout, format, dossier.idDossier(), 
                dossier.objet(), 
                dossier.dateReception());
        }
    }
    
}
