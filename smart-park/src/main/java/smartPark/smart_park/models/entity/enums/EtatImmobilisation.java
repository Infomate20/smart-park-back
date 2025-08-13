package smartPark.smart_park.models.entity.enums;

public enum EtatImmobilisation {
    EN_SERVICE("En service"),
    EN_PANNE("En panne"),
    EN_REPARATION("En réparation"),
    MISE_AU_REBUS("Mise au rebut");

    private final String libelle;
    EtatImmobilisation(String libelle) {
        this.libelle = libelle;
    }
    public String getLibelle() {
        return libelle;
    }
}
