package smartPark.smart_park.mapper;

import org.springframework.stereotype.Component;
import smartPark.smart_park.models.dto.request.AgenceRequestDto;
import smartPark.smart_park.models.dto.response.AgenceResponseDto;
import smartPark.smart_park.models.entity.Agence;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AgenceMapper {

    public Agence toEntity(AgenceRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }

        Agence agence = new Agence();
        agence.setCode(requestDto.getCode().toUpperCase()); // Toujours en majuscules
        agence.setNom(requestDto.getNom());
        agence.setDescription(requestDto.getDescription());
        agence.setAdresse(requestDto.getAdresse());
        agence.setVille(requestDto.getVille());
        agence.setCodePostal(requestDto.getCodePostal());
        agence.setPays(requestDto.getPays());
        agence.setTelephone(requestDto.getTelephone());
        agence.setFax(requestDto.getFax());
        agence.setEmail(requestDto.getEmail());
        agence.setNomResponsable(requestDto.getNomResponsable());
        agence.setTelephoneResponsable(requestDto.getTelephoneResponsable());
        agence.setEmailResponsable(requestDto.getEmailResponsable());
        agence.setActif(requestDto.getActif() != null ? requestDto.getActif() : true);

        return agence;
    }

    public AgenceResponseDto toResponseDto(Agence agence) {
        if (agence == null) {
            return null;
        }

        AgenceResponseDto responseDto = new AgenceResponseDto();
        responseDto.setId(agence.getId());
        responseDto.setCode(agence.getCode());
        responseDto.setNom(agence.getNom());
        responseDto.setDescription(agence.getDescription());
        responseDto.setAdresse(agence.getAdresse());
        responseDto.setVille(agence.getVille());
        responseDto.setCodePostal(agence.getCodePostal());
        responseDto.setPays(agence.getPays());
        responseDto.setTelephone(agence.getTelephone());
        responseDto.setFax(agence.getFax());
        responseDto.setEmail(agence.getEmail());
        responseDto.setNomResponsable(agence.getNomResponsable());
        responseDto.setTelephoneResponsable(agence.getTelephoneResponsable());
        responseDto.setEmailResponsable(agence.getEmailResponsable());
        responseDto.setActif(agence.getActif());
        responseDto.setDateCreation(agence.getDateCreation());
        responseDto.setDateModification(agence.getDateModification());

        // Statistiques
        Long nombreImmobilisations = (agence.getImmobilisations() != null)
                ? (long) agence.getImmobilisations().size()
                : 0L;
        responseDto.setNombreImmobilisations(nombreImmobilisations);

        Long nombreUtilisateurs = (agence.getUtilisateurs() != null)
                ? (long) agence.getUtilisateurs().size()
                : 0L;
        responseDto.setNombreUtilisateurs(nombreUtilisateurs);

        Long nombreTransactionsSource = (agence.getTransactionsSource() != null)
                ? (long) agence.getTransactionsSource().size()
                : 0L;
        responseDto.setNombreTransactionsSource(nombreTransactionsSource);

        Long nombreTransactionsDestination = (agence.getTransactionsDestination() != null)
                ? (long) agence.getTransactionsDestination().size()
                : 0L;
        responseDto.setNombreTransactionsDestination(nombreTransactionsDestination);

        // Informations calculées
        responseDto.setAdresseComplete(construireAdresseComplete(agence));
        responseDto.setCoordonneesComplete(construireCoordonneesComplete(agence));

        return responseDto;
    }

    public List<AgenceResponseDto> toResponseDtoList(List<Agence> agences) {
        if (agences == null) {
            return null;
        }

        return agences.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(AgenceRequestDto requestDto, Agence agence) {
        if (requestDto == null || agence == null) {
            return;
        }

        if (requestDto.getCode() != null) {
            agence.setCode(requestDto.getCode().toUpperCase());
        }
        if (requestDto.getNom() != null) {
            agence.setNom(requestDto.getNom());
        }
        if (requestDto.getDescription() != null) {
            agence.setDescription(requestDto.getDescription());
        }
        if (requestDto.getAdresse() != null) {
            agence.setAdresse(requestDto.getAdresse());
        }
        if (requestDto.getVille() != null) {
            agence.setVille(requestDto.getVille());
        }
        if (requestDto.getCodePostal() != null) {
            agence.setCodePostal(requestDto.getCodePostal());
        }
        if (requestDto.getPays() != null) {
            agence.setPays(requestDto.getPays());
        }
        if (requestDto.getTelephone() != null) {
            agence.setTelephone(requestDto.getTelephone());
        }
        if (requestDto.getFax() != null) {
            agence.setFax(requestDto.getFax());
        }
        if (requestDto.getEmail() != null) {
            agence.setEmail(requestDto.getEmail());
        }
        if (requestDto.getNomResponsable() != null) {
            agence.setNomResponsable(requestDto.getNomResponsable());
        }
        if (requestDto.getTelephoneResponsable() != null) {
            agence.setTelephoneResponsable(requestDto.getTelephoneResponsable());
        }
        if (requestDto.getEmailResponsable() != null) {
            agence.setEmailResponsable(requestDto.getEmailResponsable());
        }
        if (requestDto.getActif() != null) {
            agence.setActif(requestDto.getActif());
        }
    }

    // Méthodes utilitaires privées
    private String construireAdresseComplete(Agence agence) {
        StringBuilder adresse = new StringBuilder();

        if (agence.getAdresse() != null && !agence.getAdresse().trim().isEmpty()) {
            adresse.append(agence.getAdresse());
        }

        if (agence.getCodePostal() != null && !agence.getCodePostal().trim().isEmpty() &&
                agence.getVille() != null && !agence.getVille().trim().isEmpty()) {
            if (adresse.length() > 0) {
                adresse.append(", ");
            }
            adresse.append(agence.getCodePostal()).append(" ").append(agence.getVille());
        } else if (agence.getVille() != null && !agence.getVille().trim().isEmpty()) {
            if (adresse.length() > 0) {
                adresse.append(", ");
            }
            adresse.append(agence.getVille());
        }

        if (agence.getPays() != null && !agence.getPays().trim().isEmpty()) {
            if (adresse.length() > 0) {
                adresse.append(", ");
            }
            adresse.append(agence.getPays());
        }

        return adresse.toString();
    }

    private String construireCoordonneesComplete(Agence agence) {
        StringBuilder coordonnees = new StringBuilder();

        if (agence.getTelephone() != null && !agence.getTelephone().trim().isEmpty()) {
            coordonnees.append("Tél: ").append(agence.getTelephone());
        }

        if (agence.getFax() != null && !agence.getFax().trim().isEmpty()) {
            if (coordonnees.length() > 0) {
                coordonnees.append(" | ");
            }
            coordonnees.append("Fax: ").append(agence.getFax());
        }

        if (agence.getEmail() != null && !agence.getEmail().trim().isEmpty()) {
            if (coordonnees.length() > 0) {
                coordonnees.append(" | ");
            }
            coordonnees.append("Email: ").append(agence.getEmail());
        }

        return coordonnees.toString();
    }
}