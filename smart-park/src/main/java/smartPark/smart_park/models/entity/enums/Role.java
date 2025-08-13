package smartPark.smart_park.models.entity.enums;

public enum Role {
    ADMIN("Administrateur"),
    TECHNICIEN("Technicien"),
    AGENT("Agent");

    private final String libelle;

    Role(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
