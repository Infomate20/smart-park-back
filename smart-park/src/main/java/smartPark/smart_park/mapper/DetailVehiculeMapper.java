package smartPark.smart_park.mapper;

import org.springframework.stereotype.Component;
import smartPark.smart_park.models.dto.request.DetailVehiculeRequestDto;
import smartPark.smart_park.models.dto.response.DetailVehiculeResponseDto;
import smartPark.smart_park.models.entity.DetailVehicule;
import smartPark.smart_park.models.entity.Immobilisation;

import java.time.LocalDate;

@Component
public class DetailVehiculeMapper {

    public DetailVehicule toEntity(DetailVehiculeRequestDto requestDto, Immobilisation immobilisation) {
        if (requestDto == null) {
            return null;
        }

        DetailVehicule detail = new DetailVehicule();
        detail.setImmobilisation(immobilisation);
        appliquer(requestDto, detail);
        return detail;
    }

    public DetailVehiculeResponseDto toResponseDto(DetailVehicule detail) {
        if (detail == null) {
            return null;
        }

        DetailVehiculeResponseDto responseDto = new DetailVehiculeResponseDto();
        responseDto.setId(detail.getId());

        if (detail.getImmobilisation() != null) {
            responseDto.setImmobilisationId(detail.getImmobilisation().getId());
            responseDto.setCodeImmobilisation(detail.getImmobilisation().getCodeImmobilisation());
            responseDto.setDesignation(detail.getImmobilisation().getDesignation());
        }

        responseDto.setImmatriculation(detail.getImmatriculation());
        responseDto.setKilometrage(detail.getKilometrage());
        responseDto.setTypeCarburant(detail.getTypeCarburant());
        responseDto.setTypeCarburantLibelle(
                detail.getTypeCarburant() != null ? detail.getTypeCarburant().getLibelle() : null);
        responseDto.setPuissanceFiscale(detail.getPuissanceFiscale());
        responseDto.setNombrePlaces(detail.getNombrePlaces());

        responseDto.setDateDernierControleTechnique(detail.getDateDernierControleTechnique());
        responseDto.setDateProchainControleTechnique(detail.getDateProchainControleTechnique());
        responseDto.setControleTechniqueAJour(estEncoreValide(detail.getDateProchainControleTechnique()));

        responseDto.setCompagnieAssurance(detail.getCompagnieAssurance());
        responseDto.setNumeroPoliceAssurance(detail.getNumeroPoliceAssurance());
        responseDto.setDateExpirationAssurance(detail.getDateExpirationAssurance());
        responseDto.setAssuranceAJour(estEncoreValide(detail.getDateExpirationAssurance()));

        responseDto.setObservations(detail.getObservations());
        responseDto.setDateCreation(detail.getDateCreation());
        responseDto.setDateModification(detail.getDateModification());

        return responseDto;
    }

    /**
     * Mise à jour complète : l'endpoint véhicule fonctionne en remplacement
     * (PUT), un champ omis est donc effacé et non conservé.
     */
    public void updateEntityFromDto(DetailVehiculeRequestDto requestDto, DetailVehicule detail) {
        if (requestDto == null || detail == null) {
            return;
        }
        appliquer(requestDto, detail);
    }

    /**
     * Les immatriculations sont stockées sous forme canonique : majuscules, sans
     * espace ni séparateur. « AB-123-CD », « ab 123 cd » et « AB123CD » désignent
     * la même plaque et doivent donc entrer en collision sur le contrôle d'unicité.
     */
    public String normaliserImmatriculation(String immatriculation) {
        return immatriculation != null
                ? immatriculation.replaceAll("[^A-Za-z0-9]", "").toUpperCase()
                : null;
    }

    private void appliquer(DetailVehiculeRequestDto requestDto, DetailVehicule detail) {
        detail.setImmatriculation(normaliserImmatriculation(requestDto.getImmatriculation()));
        detail.setKilometrage(requestDto.getKilometrage());
        detail.setTypeCarburant(requestDto.getTypeCarburant());
        detail.setPuissanceFiscale(requestDto.getPuissanceFiscale());
        detail.setNombrePlaces(requestDto.getNombrePlaces());
        detail.setDateDernierControleTechnique(requestDto.getDateDernierControleTechnique());
        detail.setDateProchainControleTechnique(requestDto.getDateProchainControleTechnique());
        detail.setCompagnieAssurance(requestDto.getCompagnieAssurance());
        detail.setNumeroPoliceAssurance(requestDto.getNumeroPoliceAssurance());
        detail.setDateExpirationAssurance(requestDto.getDateExpirationAssurance());
        detail.setObservations(requestDto.getObservations());
    }

    /** {@code null} si l'échéance n'est pas renseignée : on ne conclut pas. */
    private Boolean estEncoreValide(LocalDate echeance) {
        return echeance != null ? !echeance.isBefore(LocalDate.now()) : null;
    }
}
