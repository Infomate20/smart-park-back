package smartPark.smart_park.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smartPark.smart_park.models.entity.Agence;
import smartPark.smart_park.repository.AgenceRepository;
import smartPark.smart_park.services.CodeGenerationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeGenerationServiceImpl implements CodeGenerationService {

    @Autowired
    private final AgenceRepository agenceRepository;
    @Autowired
    private static final String PREFIXE_SANS_AGENCE = "STOCK";
    @Autowired
    private static final String SEPARATEUR = "-";
    private static final String PREFIXE_CATEGORIE_INCONNUE = "BIEN";

    @Override
    public String genererCodeImmobilisation(String numeroSerie, Long agenceId, String codeCategorie) {
        // Un bien sans numéro de série constructeur est identifié par sa catégorie
        // et un horodatage ; l'appelant applique ensuite garantirUniciteCode().
        if (numeroSerie == null || numeroSerie.trim().isEmpty()) {
            String discriminant = String.format("%s%s%s",
                    normaliserCodeCategorie(codeCategorie),
                    SEPARATEUR,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

            String codeGenere = (agenceId == null)
                    ? String.format("%s%s%s", PREFIXE_SANS_AGENCE, SEPARATEUR, discriminant)
                    : String.format("%s%s%s", obtenirPrefixeAgence(agenceId), SEPARATEUR, discriminant);

            log.info("Code d'immobilisation généré sans numéro de série: {}", codeGenere);
            return codeGenere;
        }

        return genererCodeImmobilisation(numeroSerie, agenceId);
    }

    @Override
    public String genererCodeImmobilisation(String numeroSerie, Long agenceId) {
        log.info("Génération du code d'immobilisation pour numéro série: {} et agence: {}", numeroSerie, agenceId);

        if (agenceId == null) {
            return genererCodeImmobilisationSansAgence(numeroSerie);
        }

        String prefixeAgence = obtenirPrefixeAgence(agenceId);
        return genererCodeImmobilisationAvecAgence(numeroSerie, prefixeAgence);
    }

    private String normaliserCodeCategorie(String codeCategorie) {
        if (codeCategorie == null || codeCategorie.trim().isEmpty()) {
            return PREFIXE_CATEGORIE_INCONNUE;
        }
        return codeCategorie.trim().toUpperCase();
    }

    @Override
    public String genererCodeImmobilisationSansAgence(String numeroSerie) {
        log.info("Génération du code d'immobilisation sans agence pour numéro série: {}", numeroSerie);

        // Format: STOCK-[NUMERO_SERIE]-[TIMESTAMP]
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        String codeGenere = String.format("%s%s%s%s%s",
                PREFIXE_SANS_AGENCE, SEPARATEUR, numeroSerie, SEPARATEUR, timestamp);

        log.info("Code d'immobilisation généré sans agence: {}", codeGenere);
        return codeGenere;
    }

    @Override
    public String genererCodeImmobilisationAvecAgence(String numeroSerie, String codeAgence) {
        log.info("Génération du code d'immobilisation avec agence: {} pour numéro série: {}", codeAgence, numeroSerie);

        // Format: [CODE_AGENCE]-[NUMERO_SERIE]
        String codeGenere = String.format("%s%s%s", codeAgence, SEPARATEUR, numeroSerie);

        log.info("Code d'immobilisation généré avec agence: {}", codeGenere);
        return codeGenere;
    }

    @Override
    public String obtenirPrefixeAgence(Long agenceId) {
        log.info("Récupération du préfixe pour l'agence ID: {}", agenceId);

        if (agenceId == null) {
            return PREFIXE_SANS_AGENCE;
        }

        try {
            Agence agence = agenceRepository.findById(agenceId).orElse(null);

            if (agence != null) {
                // Si l'agence a un code spécifique, l'utiliser
                // Sinon, générer un code basé sur l'ID et le nom
                if (agence.getCode() != null && !agence.getCode().trim().isEmpty()) {
                    return agence.getCode().toUpperCase();
                } else {
                    // Générer un code basé sur les 3 premières lettres du nom + ID
                    String nomCourt = agence.getNom().replaceAll("[^A-Za-z]", "").toUpperCase();
                    if (nomCourt.length() >= 3) {
                        nomCourt = nomCourt.substring(0, 3);
                    } else {
                        nomCourt = String.format("AG%d", agenceId);
                    }
                    return String.format("%s%03d", nomCourt, agenceId);
                }
            }
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du préfixe agence pour ID: {}", agenceId, e);
        }

        // Fallback : générer un code basé sur l'ID seulement
        return String.format("AG%03d", agenceId);
    }
}