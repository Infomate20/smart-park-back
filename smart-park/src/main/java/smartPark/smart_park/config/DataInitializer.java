package smartPark.smart_park.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import smartPark.smart_park.models.entity.Categorie;
import smartPark.smart_park.models.entity.Utilisateur;
import smartPark.smart_park.models.entity.enums.MethodeAmortissement;
import smartPark.smart_park.models.entity.enums.Role;
import smartPark.smart_park.repository.CategorieRepository;
import smartPark.smart_park.repository.UtilisateurRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final CategorieRepository categorieRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("Vérification des données initiales...");

        // On vérifie si un utilisateur avec le rôle ADMIN existe déjà
        if (utilisateurRepository.countByRole(Role.ADMIN) == 0) {
            log.info("Aucun administrateur trouvé. Création du compte administrateur par défaut.");

            // Création de l'utilisateur ADMIN par défaut
            Utilisateur admin = new Utilisateur();

            // --- CONFIGUREZ ICI VOS IDENTIFIANTS PAR DÉFAUT ---
            admin.setNomUtilisateur("admin");
            admin.setPrenom("Admin");
            admin.setEmail("lakreoeric@gmail.com");
            admin.setTelephone("000000000"); // Optionnel
            admin.setMotDePasse(passwordEncoder.encode("Dsicentresud2024")); // Mot de passe par défaut
            admin.setRole(Role.ADMIN);
            admin.setActif(true);
            admin.setCompteVerrouille(false);

            utilisateurRepository.save(admin);

            log.info("===================================================================");
            log.info("Compte Administrateur créé avec succès !");

        } else {
            log.info("Un ou plusieurs administrateurs existent déjà. Aucune action requise.");
        }

        initialiserCategories();
    }

    /**
     * Le schéma est géré par {@code ddl-auto: update} : la colonne `code` est donc
     * créée nullable et les catégories déjà en base arrivent sans code. On les
     * rattrape ici avant de compléter le référentiel, puis on ajoute les
     * catégories de référence manquantes (biens durables, pas uniquement
     * informatiques) avec leur durée d'amortissement usuelle.
     */
    private void initialiserCategories() {
        List<Categorie> sansCode = categorieRepository.findByCodeIsNull();
        for (Categorie categorie : sansCode) {
            String code = genererCodeUnique(categorie.getNom());
            categorie.setCode(code);
            if (categorie.getMethodeAmortissement() == null) {
                categorie.setMethodeAmortissement(MethodeAmortissement.LINEAIRE);
            }
            categorieRepository.save(categorie);
            log.info("Catégorie '{}' rattrapée avec le code {}", categorie.getNom(), code);
        }

        creerCategorieSiAbsente("Matériel informatique", "INFO",
                "Postes de travail, serveurs, périphériques et matériel réseau", 36);
        creerCategorieSiAbsente("Mobilier", "MOB",
                "Mobilier de bureau et agencement", 120);
        creerCategorieSiAbsente("Véhicule", "VEH",
                "Véhicules de service et utilitaires", 60);
    }

    private void creerCategorieSiAbsente(String nom, String code, String description, int dureeMois) {
        if (categorieRepository.findByNom(nom).isPresent() || categorieRepository.findByCode(code).isPresent()) {
            return;
        }

        Categorie categorie = new Categorie();
        categorie.setNom(nom);
        categorie.setCode(code);
        categorie.setDescription(description);
        categorie.setDureeAmortissementMois(dureeMois);
        categorie.setMethodeAmortissement(MethodeAmortissement.LINEAIRE);
        categorie.setActif(true);

        categorieRepository.save(categorie);
        log.info("Catégorie de référence créée : {} ({}), amortissement {} mois", nom, code, dureeMois);
    }

    /**
     * Dérive un code court à partir du nom (4 premières lettres), suffixé si besoin
     * pour respecter la contrainte d'unicité.
     */
    private String genererCodeUnique(String nom) {
        String base = nom == null ? "" : nom.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
        if (base.isEmpty()) {
            base = "CAT";
        }
        base = base.substring(0, Math.min(4, base.length()));

        String candidat = base;
        int suffixe = 1;
        while (categorieRepository.findByCode(candidat).isPresent()) {
            candidat = base + suffixe++;
        }
        return candidat;
    }
}