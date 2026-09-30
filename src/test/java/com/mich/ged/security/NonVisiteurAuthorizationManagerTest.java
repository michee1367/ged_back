package com.mich.ged.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

/**
 * Tests unitaires du gestionnaire d'autorisation qui remplace
 * anyRequest().authenticated() : aucun contexte Spring, aucune base de donnees.
 */
class NonVisiteurAuthorizationManagerTest {

    private final NonVisiteurAuthorizationManager manager = new NonVisiteurAuthorizationManager();

    private final RequestAuthorizationContext context =
            new RequestAuthorizationContext(new MockHttpServletRequest());

    private AuthorizationResult decide(Authentication authentication) {
        Supplier<? extends Authentication> supplier = () -> authentication;
        return manager.authorize(supplier, context);
    }

    private UsernamePasswordAuthenticationToken authentifie(String... roles) {
        return new UsernamePasswordAuthenticationToken(
                "utilisateur",
                null,
                List.of(roles).stream().map(SimpleGrantedAuthority::new).toList());
    }

    @Test
    void refuseUneRequeteAnonyme() {
        Authentication anonyme = new AnonymousAuthenticationToken(
                "key",
                "anonymousUser",
                List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));

        assertThat(decide(anonyme).isGranted()).isFalse();
    }

    @Test
    void refuseUneRequeteSansAuthentication() {
        assertThat(decide(null).isGranted()).isFalse();
    }

    @Test
    void refuseUneRequeteNonAuthentifiee() {
        // Constructeur a deux arguments : le jeton n'est pas marque authentifie.
        Authentication nonAuthentifie = new UsernamePasswordAuthenticationToken(
                "utilisateur", null);

        assertThat(nonAuthentifie.isAuthenticated()).isFalse();
        assertThat(decide(nonAuthentifie).isGranted()).isFalse();
    }

    @Test
    void refuseLeRoleVisiteur() {
        assertThat(decide(authentifie("ROLE_VISIT")).isGranted()).isFalse();
    }

    @Test
    void refuseLeRoleVisiteurMemeAccompagneDUnAutreRole() {
        Authentication mixte = new UsernamePasswordAuthenticationToken(
                "utilisateur",
                null,
                List.of(new SimpleGrantedAuthority("ROLE_AGENT"), new SimpleGrantedAuthority("ROLE_VISIT")));

        assertThat(decide(mixte).isGranted()).isFalse();
    }

    @Test
    void autoriseUnRoleMetier() {
        assertThat(decide(authentifie("ROLE_AGENT")).isGranted()).isTrue();
    }

    @Test
    void autoriseUnRoleSansDroitsParticuliers() {
        assertThat(decide(authentifie("ROLE_USER")).isGranted()).isTrue();
    }

    @Test
    void neConfondPasVisiteurAvecUnPrefixeSimilaire() {
        assertThat(decide(authentifie("ROLE_VISITEUR")).isGranted()).isTrue();
    }
}
