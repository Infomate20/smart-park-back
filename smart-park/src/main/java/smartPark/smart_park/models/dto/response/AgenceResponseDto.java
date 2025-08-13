package smartPark.smart_park.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgenceResponseDto {

    private Long id;
    private String code;
    private String nom;
    private String description;
    private String adresse;
    private String ville;
    private String codePostal;
    private String pays;
    private String telephone;
    private String fax;
    private String email;
    private String nomResponsable;
    private String telephoneResponsable;
    private String emailResponsable;
    private Boolean actif;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;

    // Statistiques
    private Long nombreImmobilisations;
    private Long nombreUtilisateurs;
    private Long nombreTransactionsSource;
    private Long nombreTransactionsDestination;

    // Informations calculées
    private String adresseComplete;
    private String coordonneesComplete;
}