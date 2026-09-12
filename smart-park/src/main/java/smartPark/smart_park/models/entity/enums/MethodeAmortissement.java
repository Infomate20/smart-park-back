package smartPark.smart_park.models.entity.enums;

public enum MethodeAmortissement {
    LINEAIRE("Linéaire"),
    DEGRESSIF("Dégressif"),
    NON_AMORTISSABLE("Non amortissable");

    private final String libelle;

    MethodeAmortissement(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
