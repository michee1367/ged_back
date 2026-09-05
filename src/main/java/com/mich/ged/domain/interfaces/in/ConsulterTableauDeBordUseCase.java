package com.mich.ged.domain.interfaces.in;

public interface ConsulterTableauDeBordUseCase {
    TableauDeBordResponse obtenirStatistiques(Long idService, Long idUtilisateur);

    /**
     * TableauDeBordResponse
     */
    public record TableauDeBordResponse(
        
    ) {
    }
}
