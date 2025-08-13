package smartPark.smart_park.models.entity.enums;

public enum EtatTransaction {
    EN_ATTENTE("En attente"),
    VALIDEE("Validée"),
    REJETEE("Rejetée");

    private final String description;

    EtatTransaction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}