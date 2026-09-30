package com.mich.ged.config;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mich.ged.adapters.in.DossierController;
import com.mich.ged.adapters.in.ServiceController;
import com.mich.ged.adapters.in.UtilisateurController;
import com.mich.ged.domain.interfaces.in.DossierUseCase;
import com.mich.ged.domain.interfaces.in.EnregistrerDossierUseCase;
import com.mich.ged.domain.interfaces.in.GererServiceUseCase;
import com.mich.ged.domain.interfaces.in.GererUtilisateursUseCase;
import com.mich.ged.domain.interfaces.in.TransmettreDossierUseCase;
import com.mich.ged.domain.models.DossierModel;
import com.mich.ged.domain.models.Priorite;
import com.mich.ged.domain.models.StatutDossier;
import com.mich.ged.domain.models.UtilisateurBrefModel;
import com.mich.ged.security.CustomUserDetailservice;
import com.mich.ged.security.JwtUtils;
import com.mich.ged.security.NonVisiteurAuthorizationManager;

/**
 * Verifie la matrice d'acces : lecture des services publique, ecriture des
 * services reservee a l'administrateur, et refus du role VISIT partout ailleurs.
 */
@WebMvcTest(controllers = {ServiceController.class, UtilisateurController.class, DossierController.class})
@AutoConfigureMockMvc
// @EnableWebSecurity est active via SecurityTestConfig : la slice WebMvcTest n'inclut
// pas l'auto-configuration de securite qui fournit le bean HttpSecurity.
@Import({SecurityConfig.class, NonVisiteurAuthorizationManager.class, MatriceDaccesTest.SecurityTestConfig.class})
// Le context-path est neutralise : Spring Security route sur le chemin intra-application.
@TestPropertySource(properties = "server.servlet.context-path=")
class MatriceDaccesTest {

    @TestConfiguration
    @EnableWebSecurity
    static class SecurityTestConfig {
    }

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GererServiceUseCase gererServiceUseCase;

    @MockitoBean
    private GererUtilisateursUseCase gererUtilisateursUseCase;

    @MockitoBean
    private EnregistrerDossierUseCase enregistrerDossierUseCase;

    @MockitoBean
    private TransmettreDossierUseCase transmettreDossierUseCase;

    @MockitoBean
    private DossierUseCase dossierUseCase;

    // Le filtre JWT est reel mais reste inactif : aucun header Authorization
    // n'est envoye, l'authentification provient du post-processor.
    @MockitoBean
    private JwtUtils jwtUtils;

    @MockitoBean
    private CustomUserDetailservice customUserDetailservice;

    private UsernamePasswordAuthenticationToken utilisateur(String... roles) {
        return new UsernamePasswordAuthenticationToken(
                "utilisateur",
                null,
                List.of(roles).stream().map(SimpleGrantedAuthority::new).toList());
    }

    private DossierModel dossierMinimal() {
        return new DossierModel(
                1L,
                "DOS-2026-ABCDEFGH",
                "Objet de test",
                LocalDateTime.now(),
                LocalDate.now().plusDays(30),
                Priorite.NORMALE,
                StatutDossier.RECU,
                null,
                null,
                List.of(),
                List.of());
    }

    /* ---------------- Lecture des services : publique ---------------- */

    @Test
    void lectureDesServicesEstPubliquePourUnAnonyme() throws Exception {
        mockMvc.perform(get("/services")).andExpect(status().isOk());
    }

    @Test
    void lectureDesServicesEstPubliquePourUnVisiteur() throws Exception {
        mockMvc.perform(get("/services").with(authentication(utilisateur("ROLE_VISIT"))))
                .andExpect(status().isOk());
    }

    @Test
    void lectureDesServicesEstPubliquePourUnRoleMetier() throws Exception {
        mockMvc.perform(get("/services").with(authentication(utilisateur("ROLE_AGENT"))))
                .andExpect(status().isOk());
    }

    /* ---------------- Ecriture des services : ADMINISTRATEUR ---------------- */

    @Test
    void creationDeServiceEstRefuseeAuVisiteur() throws Exception {
        mockMvc.perform(post("/services")
                        .with(authentication(utilisateur("ROLE_VISIT")))
                        .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void creationDeServiceEstRefuseeAUnRoleMetier() throws Exception {
        mockMvc.perform(post("/services")
                        .with(authentication(utilisateur("ROLE_AGENT")))
                        .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void creationDeServiceEstRefuseeAUnAnonyme() throws Exception {
        mockMvc.perform(post("/services")
                        .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isForbidden());
    }

    /* ---------------- Annuaire des utilisateurs ---------------- */

    @Test
    void annuaireEstRefuseAuVisiteur() throws Exception {
        mockMvc.perform(get("/utilisateurs/annuaire")
                        .with(authentication(utilisateur("ROLE_VISIT"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void annuaireEstRefuseAUnAnonyme() throws Exception {
        mockMvc.perform(get("/utilisateurs/annuaire")).andExpect(status().isForbidden());
    }

    @Test
    void annuaireEstAccessibleAuxAutresUtilisateurs() throws Exception {
        when(gererUtilisateursUseCase.listerAnnuaire())
                .thenReturn(List.of(new UtilisateurBrefModel(1L, "KABANGA MUKENDI Jean")));

        mockMvc.perform(get("/utilisateurs/annuaire")
                        .with(authentication(utilisateur("ROLE_AGENT"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idUtilisateur").value(1))
                .andExpect(jsonPath("$[0].nomComplet").value("KABANGA MUKENDI Jean"))
                .andExpect(jsonPath("$[0].motDePasse").doesNotExist());
    }

    @Test
    void annuaireEstAccessibleAUnAdministrateur() throws Exception {
        when(gererUtilisateursUseCase.listerAnnuaire()).thenReturn(List.of());

        mockMvc.perform(get("/utilisateurs/annuaire")
                        .with(authentication(utilisateur("ROLE_ADMINISTRATEUR"))))
                .andExpect(status().isOk());
    }

    /* ---------------- Blocage VISIT sur le reste de l'API ---------------- */

    @Test
    void lectureDossierEstRefuseeAuVisiteur() throws Exception {
        mockMvc.perform(get("/dossiers/1").with(authentication(utilisateur("ROLE_VISIT"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void lectureDossierEstRefuseeAUnAnonyme() throws Exception {
        mockMvc.perform(get("/dossiers/1")).andExpect(status().isForbidden());
    }

    @Test
    void lectureDossierEstOuverteAuxAutresUtilisateurs() throws Exception {
        when(dossierUseCase.un(1L)).thenReturn(dossierMinimal());

        mockMvc.perform(get("/dossiers/1").with(authentication(utilisateur("ROLE_AGENT"))))
                .andExpect(status().isOk());
    }

    @Test
    void telechargementDeDocumentEstRefuseAuVisiteur() throws Exception {
        mockMvc.perform(get("/dossiers/documents/1/download")
                        .with(authentication(utilisateur("ROLE_VISIT"))))
                .andExpect(status().isForbidden());
    }
}
