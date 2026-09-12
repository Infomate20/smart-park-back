package smartPark.smart_park.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import smartPark.smart_park.models.dto.response.PlanAmortissementDto;

import java.time.LocalDate;

public interface AmortissementService {

    /** Plan complet d'un bien, tableau exercice par exercice inclus. */
    PlanAmortissementDto calculerPlan(Long immobilisationId);

    /** Situation d'un bien à une date donnée (cumul et VNC), sans le tableau. */
    PlanAmortissementDto calculerSituation(Long immobilisationId, LocalDate dateSituation);

    /** Synthèse du parc à une date donnée, paginée et sans les tableaux détaillés. */
    Page<PlanAmortissementDto> obtenirSyntheseParc(LocalDate dateSituation, Pageable pageable);
}
