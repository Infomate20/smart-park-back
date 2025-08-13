package smartPark.smart_park.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import smartPark.smart_park.exceptions.ResourceNotFoundException;
import smartPark.smart_park.models.dto.request.UtilisateurRequestDto;
import smartPark.smart_park.models.dto.response.UtilisateurResponseDto;
import smartPark.smart_park.models.entity.Agence;
import smartPark.smart_park.models.entity.Utilisateur;
import smartPark.smart_park.repository.AgenceRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UtilisateurMapper {
    @Autowired
    private final AgenceRepository agenceRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public Utilisateur toEntity(UtilisateurRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNomUtilisateur(requestDto.getNomUtilisateur().toLowerCase());

        // Encoder le mot de passe si fourni
        if (requestDto.getMotDePasse() != null && !requestDto.getMotDePasse().trim().isEmpty()) {
            utilisateur.setMotDePasse(passwordEncoder.encode(requestDto.getMotDePasse()));
        }

        utilisateur.setPrenom(requestDto.getPrenom());
        utilisateur.setEmail(requestDto.getEmail());
        utilisateur.setTelephone(requestDto.getTelephone());
        utilisateur.setMatricule(requestDto.getMatricule());
        utilisateur.setRole(requestDto.getRole());
        utilisateur.setDateEmbauche(requestDto.getDateEmbauche());
        utilisateur.setPoste(requestDto.getPoste());
        utilisateur.setAdresse(requestDto.getAdresse());
        utilisateur.setDateNaissance(requestDto.getDateNaissance());
        utilisateur.setActif(requestDto.getActif() != null ? requestDto.getActif() : true);

        // Relation avec l'agence
        if (requestDto.getAgenceId() != null) {
            Agence agence = agenceRepository.findById(requestDto.getAgenceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence non trouvée avec l'ID: " + requestDto.getAgenceId()));
            utilisateur.setAgence(agence);
        }

        return utilisateur;
    }

    public UtilisateurResponseDto toResponseDto(Utilisateur utilisateur) {
        if (utilisateur == null) {
            return null;
        }

        UtilisateurResponseDto responseDto = new UtilisateurResponseDto();
        responseDto.setId(utilisateur.getId());
        responseDto.setNomUtilisateur(utilisateur.getNomUtilisateur());
        responseDto.setPrenom(utilisateur.getPrenom());
        responseDto.setEmail(utilisateur.getEmail());
        responseDto.setTelephone(utilisateur.getTelephone());
        responseDto.setMatricule(utilisateur.getMatricule());
        responseDto.setRole(utilisateur.getRole());
        responseDto.setRoleLibelle(utilisateur.getRole().getLibelle());
        responseDto.setDateEmbauche(utilisateur.getDateEmbauche());
        responseDto.setPoste(utilisateur.getPoste());
        responseDto.setAdresse(utilisateur.getAdresse());
        responseDto.setDateNaissance(utilisateur.getDateNaissance());
        responseDto.setDerniereConnexion(utilisateur.getDerniereConnexion());
        responseDto.setTentativesConnexionEchouees(utilisateur.getTentativesConnexionEchouees());
        responseDto.setCompteVerrouille(utilisateur.getCompteVerrouille());
        responseDto.setMotDePasseExpire(utilisateur.getMotDePasseExpire());
        responseDto.setPremiereConnexion(utilisateur.getPremiereConnexion());
        responseDto.setActif(utilisateur.getActif());
        responseDto.setDateCreation(utilisateur.getDateCreation());
        responseDto.setDateModification(utilisateur.getDateModification());

        // Informations de l'agence
        if (utilisateur.getAgence() != null) {
            responseDto.setAgenceId(utilisateur.getAgence().getId());
            responseDto.setAgenceNom(utilisateur.getAgence().getNom());
            responseDto.setAgenceCode(utilisateur.getAgence().getCode());
        }

        // Statistiques
        Long nombreInterventions = (utilisateur.getInterventions() != null)
                ? (long) utilisateur.getInterventions().size()
                : 0L;
        responseDto.setNombreInterventions(nombreInterventions);

        Long nombreTransactionsCreees = (utilisateur.getTransactionsCreees() != null)
                ? (long) utilisateur.getTransactionsCreees().size()
                : 0L;
        responseDto.setNombreTransactionsCreees(nombreTransactionsCreees);

        // Informations calculées
        responseDto.setNomComplet(utilisateur.getPrenom() + " " + utilisateur.getNomUtilisateur());
        responseDto.setAgeEnAnnees(calculerAge(utilisateur.getDateNaissance()));
        responseDto.setAncienneteEnAnnees(calculerAnciennete(utilisateur.getDateEmbauche()));
        responseDto.setStatutCompte(determinerStatutCompte(utilisateur));

        return responseDto;
    }

    public List<UtilisateurResponseDto> toResponseDtoList(List<Utilisateur> utilisateurs) {
        if (utilisateurs == null) {
            return null;
        }

        return utilisateurs.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(UtilisateurRequestDto requestDto, Utilisateur utilisateur) {
        if (requestDto == null || utilisateur == null) {
            return;
        }

        if (requestDto.getNomUtilisateur() != null) {
            utilisateur.setNomUtilisateur(requestDto.getNomUtilisateur().toLowerCase());
        }

        // Ne pas mettre à jour le mot de passe ici (méthode séparée)

        if (requestDto.getNomUtilisateur() != null) {
            utilisateur.setNomUtilisateur(requestDto.getNomUtilisateur());
        }
        if (requestDto.getPrenom() != null) {
            utilisateur.setPrenom(requestDto.getPrenom());
        }
        if (requestDto.getEmail() != null) {
            utilisateur.setEmail(requestDto.getEmail());
        }
        if (requestDto.getTelephone() != null) {
            utilisateur.setTelephone(requestDto.getTelephone());
        }
        if (requestDto.getMatricule() != null) {
            utilisateur.setMatricule(requestDto.getMatricule());
        }
        if (requestDto.getRole() != null) {
            utilisateur.setRole(requestDto.getRole());
        }
        if (requestDto.getDateEmbauche() != null) {
            utilisateur.setDateEmbauche(requestDto.getDateEmbauche());
        }
        if (requestDto.getPoste() != null) {
            utilisateur.setPoste(requestDto.getPoste());
        }
        if (requestDto.getAdresse() != null) {
            utilisateur.setAdresse(requestDto.getAdresse());
        }
        if (requestDto.getDateNaissance() != null) {
            utilisateur.setDateNaissance(requestDto.getDateNaissance());
        }
        if (requestDto.getActif() != null) {
            utilisateur.setActif(requestDto.getActif());
        }

        // Mise à jour de l'agence
        if (requestDto.getAgenceId() != null) {
            Agence agence = agenceRepository.findById(requestDto.getAgenceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence non trouvée avec l'ID: " + requestDto.getAgenceId()));
            utilisateur.setAgence(agence);
        } else {
            utilisateur.setAgence(null);
        }
    }

    // Méthodes utilitaires privées
    private Integer calculerAge(LocalDate dateNaissance) {
        if (dateNaissance == null) {
            return null;
        }
        return Period.between(dateNaissance, LocalDate.now()).getYears();
    }

    private Integer calculerAnciennete(LocalDate dateEmbauche) {
        if (dateEmbauche == null) {
            return null;
        }
        return Period.between(dateEmbauche, LocalDate.now()).getYears();
    }

    private String determinerStatutCompte(Utilisateur utilisateur) {
        if (!utilisateur.getActif()) {
            return "Inactif";
        }
        if (utilisateur.getCompteVerrouille()) {
            return "Verrouillé";
        }
        if (utilisateur.getMotDePasseExpire()) {
            return "Mot de passe expiré";
        }
        if (utilisateur.getPremiereConnexion()) {
            return "Première connexion";
        }
        return "Actif";
    }
}