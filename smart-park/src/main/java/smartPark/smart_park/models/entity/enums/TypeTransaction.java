package smartPark.smart_park.models.entity.enums;

public enum TypeTransaction {
    TRANSFERT("Transfert"),
    AFFECTATION("Affectation"),
    DESAFFECTATION("Désaffectation");

    private final String description;

    TypeTransaction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}