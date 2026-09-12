package smartPark.smart_park.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import smartPark.smart_park.models.dto.request.DetailVehiculeRequestDto;
import smartPark.smart_park.models.dto.response.DetailVehiculeResponseDto;

public interface DetailVehiculeService {

    /** Crée ou remplace les informations véhicule d'une immobilisation. */
    DetailVehiculeResponseDto enregistrerDetailVehicule(Long immobilisationId, DetailVehiculeRequestDto requestDto);

    DetailVehiculeResponseDto obtenirDetailVehicule(Long immobilisationId);

    DetailVehiculeResponseDto obtenirParImmatriculation(String immatriculation);

    Page<DetailVehiculeResponseDto> obtenirTousLesVehicules(Pageable pageable);

    void supprimerDetailVehicule(Long immobilisationId);
}
