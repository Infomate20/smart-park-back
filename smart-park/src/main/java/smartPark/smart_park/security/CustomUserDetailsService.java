package smartPark.smart_park.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import smartPark.smart_park.models.entity.Utilisateur;
import smartPark.smart_park.repository.UtilisateurRepository;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByNomUtilisateurOrEmailOrTelephone(identifier,identifier,identifier)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + identifier));

        return new CustomUserPrincipal(utilisateur);
    }

    public static class CustomUserPrincipal implements UserDetails {
        private final Utilisateur utilisateur;

        public CustomUserPrincipal(Utilisateur utilisateur) {
            this.utilisateur = utilisateur;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().name())
            );
        }

        @Override
        public String getPassword() {
            return utilisateur.getMotDePasse();
        }

        @Override
        public String getUsername() {
            return utilisateur.getNomUtilisateur();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return !utilisateur.getCompteVerrouille();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return !utilisateur.getMotDePasseExpire();
        }

        @Override
        public boolean isEnabled() {
            return utilisateur.getActif();
        }

        public Utilisateur getUtilisateur() {
            return utilisateur;
        }
    }
}