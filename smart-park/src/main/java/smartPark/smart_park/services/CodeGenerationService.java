package smartPark.smart_park.services;

public interface CodeGenerationService {
    String genererCodeImmobilisation(String numeroSerie, Long agenceId);
    String genererCodeImmobilisationSansAgence(String numeroSerie);
    String genererCodeImmobilisationAvecAgence(String numeroSerie, String codeAgence);
    String obtenirPrefixeAgence(Long agenceId);
}
