package smartPark.smart_park.models.entity.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum EtatIntervention {
    EN_COURS("En cours"),
    PLANIFIER("Planifié"),
    TERMINER("Terminée"),
    ANNULER("Annulée");
    private String libelle ;

    public String getDescription(){
        return this.libelle;
    }
}
