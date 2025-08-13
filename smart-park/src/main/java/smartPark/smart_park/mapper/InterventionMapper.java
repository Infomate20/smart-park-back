package smartPark.smart_park.mapper;

import org.springframework.stereotype.Component;
import smartPark.smart_park.models.dto.request.InterventionRequestDto;
import smartPark.smart_park.models.dto.request.InterventionUpdateDto;
import smartPark.smart_park.models.dto.response.InterventionResponseDto;
import smartPark.smart_park.models.entity.Immobilisation;
import smartPark.smart_park.models.entity.Intervention;
import smartPark.smart_park.models.entity.Utilisateur;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InterventionMapper {

    public Intervention toEntity(InterventionRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Intervention intervention = new Intervention();
        intervention.setDescription(dto.getDescription());
        intervention.setDateIntervention(dto.getDateIntervention());
        intervention.setCoutIntervention(dto.getCoutIntervention());
        intervention.setObservations(dto.getObservations());
        intervention.setTypeIntervention(dto.getTypeIntervention());
        intervention.setOrientation(dto.getOrientation());

        // Les relations seront définies dans le service
        if (dto.getImmobilisationId() != null) {
            Immobilisation immobilisation = new Immobilisation();
            immobilisation.setId(dto.getImmobilisationId());
            intervention.setImmobilisation(immobilisation);
        }

        if (dto.getTechnicienId() != null) {
            Utilisateur technicien = new Utilisateur();
            technicien.setId(dto.getTechnicienId());
            intervention.setTechnicien(technicien);
        }

        return intervention;
    }

    public InterventionResponseDto toResponseDto(Intervention intervention) {
        if (intervention == null) {
            return null;
        }

        InterventionResponseDto dto = new InterventionResponseDto();
        dto.setId(intervention.getId());
        dto.setDescription(intervention.getDescription());
        dto.setDateIntervention(intervention.getDateIntervention());
        dto.setCoutIntervention(intervention.getCoutIntervention());
        dto.setObservations(intervention.getObservations());
        dto.setDateCloture(intervention.getDateCloture());
        dto.setEtatIntervention(intervention.getEtatIntervention());
        dto.setTypeIntervention(intervention.getTypeIntervention());
        dto.setOrientation(intervention.getOrientation());
        dto.setCreatedAt(intervention.getCreatedAt());
        dto.setUpdatedAt(intervention.getUpdatedAt());

        // Informations de l'immobilisation
        if (intervention.getImmobilisation() != null) {
            dto.setImmobilisationId(intervention.getImmobilisation().getId());
            dto.setImmobilisationCode(intervention.getImmobilisation().getCodeImmobilisation());
            dto.setImmobilisationDesignation(intervention.getImmobilisation().getDesignation());

            // Informations de l'agence via l'immobilisation
            if (intervention.getImmobilisation().getAgence() != null) {
                dto.setAgenceId(intervention.getImmobilisation().getAgence().getId());
                dto.setAgenceNom(intervention.getImmobilisation().getAgence().getNom());
            }
        }

        // Informations du technicien
        if (intervention.getTechnicien() != null) {
            dto.setTechnicienId(intervention.getTechnicien().getId());
            dto.setNomUtilisateur(intervention.getTechnicien().getNomUtilisateur());
           // dto.setTechnicienNomUtilisateur(intervention.getTechnicien().getNomUtilisateur());
            dto.setTechnicienPrenom(intervention.getTechnicien().getPrenom());
            dto.setTechnicienEmail(intervention.getTechnicien().getEmail());
        }

        return dto;
    }

    public void updateEntity(Intervention intervention, InterventionUpdateDto dto) {
        if (dto == null || intervention == null) {
            return;
        }

        if (dto.getDescription() != null) {
            intervention.setDescription(dto.getDescription());
        }
        if (dto.getDateIntervention() != null) {
            intervention.setDateIntervention(dto.getDateIntervention());
        }
        if (dto.getCoutIntervention() != null) {
            intervention.setCoutIntervention(dto.getCoutIntervention());
        }
        if (dto.getObservations() != null) {
            intervention.setObservations(dto.getObservations());
        }
        if (dto.getTypeIntervention() != null) {
            intervention.setTypeIntervention(dto.getTypeIntervention());
        }
        if (dto.getOrientation() != null) {
            intervention.setOrientation(dto.getOrientation());
        }

        // Les relations seront mises à jour dans le service si nécessaire
        if (dto.getImmobilisationId() != null) {
            Immobilisation immobilisation = new Immobilisation();
            immobilisation.setId(dto.getImmobilisationId());
            intervention.setImmobilisation(immobilisation);
        }

        if (dto.getTechnicienId() != null) {
            Utilisateur technicien = new Utilisateur();
            technicien.setId(dto.getTechnicienId());
            intervention.setTechnicien(technicien);
        }
    }

    public List<InterventionResponseDto> toResponseDtoList(List<Intervention> interventions) {
        if (interventions == null) {
            return null;
        }

        return interventions.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}