package com.mich.ged.adapters.in;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mich.ged.domain.interfaces.in.DashboardUseCase;
import com.mich.ged.domain.interfaces.in.DashboardUseCase.KpisDashboard;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="dashboard", description="KPIs du tableau de bord")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardUseCase dashboardUseCase;

    public DashboardController(DashboardUseCase dashboardUseCase) {
        this.dashboardUseCase = dashboardUseCase;
    }

    @Operation(summary="fourni les indicateurs du tableau de bord")
    @GetMapping("/kpis")
    public ResponseEntity<KpisDashboard> kpis(
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        KpisDashboard result = dashboardUseCase.getKpis(userDetails.getUsername());
        return ResponseEntity.ok(result);
    }
}