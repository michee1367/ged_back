package com.mich.ged.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mich.ged.domain.interfaces.out.UtilisateurRepositoryPort;
import com.mich.ged.domain.models.TypeRole;
import com.mich.ged.domain.models.UtilisateurModel;

@Service
public class CustomUserDetailservice implements UserDetailsService {

    private final UtilisateurRepositoryPort utilisateurRepository;
    //private GenericMapper mapper;

    public CustomUserDetailservice(
        UtilisateurRepositoryPort utilisateurRepository
    ) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UtilisateurModel user = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + username));
        
        Collection<SimpleGrantedAuthority> authorities;

        if (user.roles() == null) {
            authorities = List.of(new SimpleGrantedAuthority("User_ROLE") );
        }else {
            authorities = user.roles().stream().map((TypeRole role) -> new SimpleGrantedAuthority(role.name()+ "_ROLE")).toList();
        }
        
        return new User(
                user.phoneNumber(),
                user.motDePasse(), // Hash BCrypt récupéré depuis la base
                //List.of(new SimpleGrantedAuthority(user.getRole()))
                //List.of(new SimpleGrantedAuthority("User_ROLE") )
                authorities
        );
        
    }
}
