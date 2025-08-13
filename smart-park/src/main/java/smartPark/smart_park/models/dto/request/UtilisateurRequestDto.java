package smartPark.smart_park.models.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartPark.smart_park.models.entity.enums.Role;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurRequestDto {

    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit contenir entre 3 et 50 caractères")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Le nom d'utilisateur ne peut contenir que des lettres, chiffres, points, tirets et underscores")
    private String nomUtilisateur;

    @Size(min = 8, max = 255, message = "Le mot de passe doit contenir au moins 8 caractères")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message = "Le mot de passe doit contenir au moins une minuscule, une majuscule et un chiffre")
    private String motDePasse;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 100, message = "Le prénom doit contenir entre 2 et 100 caractères")
    private String prenom;

    @Email(message = "Format d'email invalide")
    @Size(max = 100, message = "L'email ne peut pas dépasser 100 caractères")
    private String email;

    @Size(max = 20, message = "Le téléphone ne peut pas dépasser 20 caractères")
    @Pattern(regexp = "^[0-9+\\-\\s]*$", message = "Le téléphone contient des caractères invalides")
    private String telephone;

    @Size(max = 10, message = "Le matricule ne peut pas dépasser 10 caractères")
    private String matricule;

    @NotNull(message = "Le rôle est obligatoire")
    private Role role;

    @Past(message = "La date d'embauche doit être dans le passé")
    private LocalDate dateEmbauche;

    @Size(max = 100, message = "Le poste ne peut pas dépasser 100 caractères")
    private String poste;

    @Size(max = 500, message = "L'adresse ne peut pas dépasser 500 caractères")
    private String adresse;

    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateNaissance;

    private Long agenceId; // Optionnel

    @NotNull(message = "Le statut actif est obligatoire")
    private Boolean actif = true;
}
