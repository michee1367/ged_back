package com.mich.ged.domain.interfaces.in;



public interface  DashboardUseCase {

    KpisDashboard getKpis(String idUtilisateur);

    /**
     * KpisDashboard
     */
    public record KpisDashboard(
        int totalDossiers,
        int totalDocuments,
        int totalServicesRattaches,
        int totalUtilisateurs,
        int totalDossiersEncours,
        int totalDossiersTraites,
        int totalDossiersArchivees,
        int totalDossiersUrgents
    ) {
    }
}
