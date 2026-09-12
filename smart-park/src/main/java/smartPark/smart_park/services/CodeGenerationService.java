package smartPark.smart_park.services;

public interface CodeGenerationService {
    /**
     * Point d'entrée unique : si {@code numeroSerie} est absent (biens sans numéro
     * de série constructeur, typiquement le mobilier), le code est dérivé de
     * {@code codeCategorie} au lieu du numéro de série.
     */
    String genererCodeImmobilisation(String numeroSerie, Long agenceId, String codeCategorie);

    String genererCodeImmobilisation(String numeroSerie, Long agenceId);
    String genererCodeImmobilisationSansAgence(String numeroSerie);
    String genererCodeImmobilisationAvecAgence(String numeroSerie, String codeAgence);
    String obtenirPrefixeAgence(Long agenceId);
}
