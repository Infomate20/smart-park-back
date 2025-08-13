package smartPark.smart_park.models.entity.enums;

public enum Orientation {
    VERS_REBUS("Vers rebus"),
    VERS_AGENCE("Vers agence");

    private final String description;

    Orientation(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}