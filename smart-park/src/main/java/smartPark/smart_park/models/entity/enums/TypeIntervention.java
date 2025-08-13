package smartPark.smart_park.models.entity.enums;

public enum TypeIntervention {
    MAINTENANCE_PREVENTIVE("Maintenance préventive"),
    MAINTENANCE_CORRECTIVE("Maintenance corrective"),
    INSTALLATION("Installation"),
    DESINSTALLATION("Désinstallation");

    private final String description;

    TypeIntervention(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}