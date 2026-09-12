package smartPark.smart_park.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartPark.smart_park.models.entity.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurResponseDto {

    private Long id;
    private String nomUtilisateur;
    // Ne pas exposer le mot de passe
    private String prenom;
    private String email;
    private String telephone;
    private String matricule;
    private Role role;
    private String roleLibelle;
    private LocalDate dateEmbauche;
    private String poste;
    private String adresse;
    private LocalDate dateNaissance;
    private LocalDateTime derniereConnexion;
    private Integer tentativesConnexionEchouees;
    private Boolean compteVerrouille;
    private Boolean motDePasseExpire;
    private Boolean premiereConnexion;
    private Boolean actif;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;

    // Informations de l'agence
    private Long agenceId;
    private String agenceNom;
    private String agenceCode;

    // Statistiques
    private Long nombreInterventions;
    private Long nombreTransactionsCreees;

    // Informations calculées
    private String nomComplet;
    private Integer ageEnAnnees;
    private Integer ancienneteEnAnnees;
    private String statutCompte;
}