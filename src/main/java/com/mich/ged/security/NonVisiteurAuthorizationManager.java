package com.mich.ged.security;

import java.util.function.Supplier;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

/**
 * Refuse l'accès à tout endpoint sécurisé aux utilisateurs portant le rôle VISIT.
 *
 * Les requêtes non authentifiées sont également refusées ici : ce gestionnaire
 * remplace anyRequest().authenticated(), il doit donc assurer les deux contrôles.
 * Le cas anonyme est traité explicitement car AnonymousAuthenticationToken
 * renvoie isAuthenticated() == true.
 */
@Component
public class NonVisiteurAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    public static final String ROLE_VISIT = "ROLE_VISIT";

    private final AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

    @Override
    public AuthorizationResult authorize(
            Supplier<? extends Authentication> authenticationSupplier,
            RequestAuthorizationContext context) {

        Authentication authentication = authenticationSupplier.get();

        if (authentication == null
                || !authentication.isAuthenticated()
                || trustResolver.isAnonymous(authentication)) {
            return new AuthorizationDecision(false);
        }

        boolean estVisiteur = authentication.getAuthorities().stream()
                .anyMatch(authority -> ROLE_VISIT.equals(authority.getAuthority()));

        return new AuthorizationDecision(!estVisiteur);
    }
}
