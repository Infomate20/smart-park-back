package smartPark.smart_park.models.entity.enums;

public enum EtatTicket {
    NOUVEAU("Nouveau"),
    VALIDE("Validé (Panne confirmée)"),
    REJETE("Rejeté"),
    EN_COURS_DE_TRAITEMENT("En cours de traitement"),
    RESOLU("Résolu");

    private final String libelle;

    EtatTicket(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
