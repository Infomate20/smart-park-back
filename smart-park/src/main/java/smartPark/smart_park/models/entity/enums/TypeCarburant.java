package smartPark.smart_park.models.entity.enums;

public enum TypeCarburant {
    ESSENCE("Essence"),
    DIESEL("Diesel"),
    HYBRIDE("Hybride"),
    ELECTRIQUE("Électrique"),
    GPL("GPL");

    private final String libelle;

    TypeCarburant(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
