package smartPark.smart_park.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import smartPark.smart_park.exceptions.ResourceNotFoundException;
import smartPark.smart_park.models.dto.request.ImmobilisationRequestDto;
import smartPark.smart_park.models.dto.response.ImmobilisationResponseDto;
import smartPark.smart_park.models.entity.Agence;
import smartPark.smart_park.models.entity.Categorie;
import smartPark.smart_park.models.entity.Immobilisation;
import smartPark.smart_park.repository.AgenceRepository;
import smartPark.smart_park.repository.CategorieRepository;
import smartPark.smart_park.services.CodeGenerationService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ImmobilisationMapper {
    @Autowired
    private final CategorieRepository categorieRepository;
    @Autowired
    private final AgenceRepository agenceRepository;
    @Autowired
    private final CodeGenerationService codeGenerationService;

    public Immobilisation toEntity(ImmobilisationRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }
        Immobilisation immobilisation = new Immobilisation();
        immobilisation.setNumeroSerie(requestDto.getNumeroSerie());
        immobilisation.setDesignation(requestDto.getDesignation());
        immobilisation.setMarque(requestDto.getMarque());
        immobilisation.setModele(requestDto.getModele());
        immobilisation.setDateAcquisition(requestDto.getDateAcquisition());
        immobilisation.setPrixAcquisition(requestDto.getPrixAcquisition());
        immobilisation.setValeurActuelle(requestDto.getValeurActuelle());
        immobilisation.setEtat(requestDto.getEtat());
        immobilisation.setDateMiseEnService(requestDto.getDateMiseEnService());
        immobilisation.setDureeGarantieMois(requestDto.getDureeGarantieMois());
        immobilisation.setObservations(requestDto.getObservations());
        immobilisation.setActif(requestDto.getActif() != null ? requestDto.getActif() : true);
        // Relations : résolues avant la génération du code, qui s'appuie sur le
        // code de catégorie lorsque le bien n'a pas de numéro de série
        if (requestDto.getCategorieId() != null) {
            Categorie categorie = categorieRepository.findById(requestDto.getCategorieId())
                    .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée avec l'ID: " + requestDto.getCategorieId()));
            immobilisation.setCategorie(categorie);
        }
        if (requestDto.getAgenceId() != null) {
            Agence agence = agenceRepository.findById(requestDto.getAgenceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence non trouvée avec l'ID: " + requestDto.getAgenceId()));
            immobilisation.setAgence(agence);
        }
        String codeGenere = codeGenerationService.genererCodeImmobilisation(
                requestDto.getNumeroSerie(),
                requestDto.getAgenceId(),
                codeCategorie(immobilisation)
        );
        immobilisation.setCodeImmobilisation(codeGenere);
        return immobilisation;
    }
    public ImmobilisationResponseDto toResponseDto(Immobilisation immobilisation) {
        if (immobilisation == null) {
            return null;
        }
        ImmobilisationResponseDto responseDto = new ImmobilisationResponseDto();
        responseDto.setId(immobilisation.getId());
        responseDto.setCodeImmobilisation(immobilisation.getCodeImmobilisation());
        responseDto.setNumeroSerie(immobilisation.getNumeroSerie());
        responseDto.setDesignation(immobilisation.getDesignation());
        responseDto.setMarque(immobilisation.getMarque());
        responseDto.setModele(immobilisation.getModele());
        responseDto.setDateAcquisition(immobilisation.getDateAcquisition());
        responseDto.setPrixAcquisition(immobilisation.getPrixAcquisition());
        responseDto.setValeurActuelle(immobilisation.getValeurActuelle());
        responseDto.setEtat(immobilisation.getEtat());
        responseDto.setEtatLibelle(immobilisation.getEtat().getLibelle());
        responseDto.setDateMiseEnService(immobilisation.getDateMiseEnService());
        responseDto.setDureeGarantieMois(immobilisation.getDureeGarantieMois());
        responseDto.setObservations(immobilisation.getObservations());
        responseDto.setActif(immobilisation.getActif());
        responseDto.setDateCreation(immobilisation.getDateCreation());
        responseDto.setDateModification(immobilisation.getDateModification());
        // Informations de la catégorie
        if (immobilisation.getCategorie() != null) {
            responseDto.setCategorieId(immobilisation.getCategorie().getId());
            responseDto.setCategorieNom(immobilisation.getCategorie().getNom());
        }
        // Informations de l'agence
        if (immobilisation.getAgence() != null) {
            responseDto.setAgenceId(immobilisation.getAgence().getId());
            responseDto.setAgenceNom(immobilisation.getAgence().getNom());
        }
        // Statistiques
        Long nombreInterventions = (immobilisation.getInterventions() != null)
                ? (long) immobilisation.getInterventions().size()
                : 0L;
        responseDto.setNombreInterventions(nombreInterventions);
        Long nombreTransactions = (immobilisation.getTransactions() != null)
                ? (long) immobilisation.getTransactions().size()
                : 0L;
        responseDto.setNombreTransactions(nombreTransactions);
        // Calculs
        responseDto.setSousGarantie(calculerSousGarantie(immobilisation));
        responseDto.setAgeEnMois(calculerAgeEnMois(immobilisation));
        return responseDto;
    }
    public List<ImmobilisationResponseDto> toResponseDtoList(List<Immobilisation> immobilisations) {
        if (immobilisations == null) {
            return null;
        }
        return immobilisations.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
    public void updateEntityFromDto(ImmobilisationRequestDto requestDto, Immobilisation immobilisation) {
        if (requestDto == null || immobilisation == null) {
            return;
        }
        boolean regenererCode = false;

        if (requestDto.getNumeroSerie() != null && !requestDto.getNumeroSerie().equals(immobilisation.getNumeroSerie())) {
            immobilisation.setNumeroSerie(requestDto.getNumeroSerie());
            regenererCode = true;
        }
        if (requestDto.getDesignation() != null) {
            immobilisation.setDesignation(requestDto.getDesignation());
        }
        if (requestDto.getMarque() != null) {
            immobilisation.setMarque(requestDto.getMarque());
        }
        if (requestDto.getModele() != null) {
            immobilisation.setModele(requestDto.getModele());
        }
        if (requestDto.getDateAcquisition() != null) {
            immobilisation.setDateAcquisition(requestDto.getDateAcquisition());
        }
        if (requestDto.getPrixAcquisition() != null) {
            immobilisation.setPrixAcquisition(requestDto.getPrixAcquisition());
        }
        if (requestDto.getValeurActuelle() != null) {
            immobilisation.setValeurActuelle(requestDto.getValeurActuelle());
        }
        if (requestDto.getEtat() != null) {
            immobilisation.setEtat(requestDto.getEtat());
        }
        if (requestDto.getDateMiseEnService() != null) {
            immobilisation.setDateMiseEnService(requestDto.getDateMiseEnService());
        }
        if (requestDto.getDureeGarantieMois() != null) {
            immobilisation.setDureeGarantieMois(requestDto.getDureeGarantieMois());
        }
        if (requestDto.getObservations() != null) {
            immobilisation.setObservations(requestDto.getObservations());
        }
        if (requestDto.getActif() != null) {
            immobilisation.setActif(requestDto.getActif());
        }
        // Mise à jour des relations
        if (requestDto.getCategorieId() != null) {
            Categorie categorie = categorieRepository.findById(requestDto.getCategorieId())
                    .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée avec l'ID: " + requestDto.getCategorieId()));
            immobilisation.setCategorie(categorie);
        }
        // Gestion de l'agence avec régénération du code si nécessaire
        Long ancienneAgenceId = immobilisation.getAgence() != null ? immobilisation.getAgence().getId() : null;
        if ((requestDto.getAgenceId() == null && ancienneAgenceId != null) ||
                (requestDto.getAgenceId() != null && !requestDto.getAgenceId().equals(ancienneAgenceId))) {
            regenererCode = true;
        }
        if (requestDto.getAgenceId() != null) {
            Agence agence = agenceRepository.findById(requestDto.getAgenceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence non trouvée avec l'ID: " + requestDto.getAgenceId()));
            immobilisation.setAgence(agence);
        } else {
            immobilisation.setAgence(null);
        }
        // Régénérer le code si nécessaire
        if (regenererCode) {
            String codeGenere = codeGenerationService.genererCodeImmobilisation(
                    immobilisation.getNumeroSerie(),
                    requestDto.getAgenceId(),
                    codeCategorie(immobilisation)
            );
            immobilisation.setCodeImmobilisation(codeGenere);
        }
    }

    /** Code de la catégorie du bien, ou {@code null} si elle n'est pas encore résolue. */
    private String codeCategorie(Immobilisation immobilisation) {
        return immobilisation.getCategorie() != null ? immobilisation.getCategorie().getCode() : null;
    }
    private Boolean calculerSousGarantie(Immobilisation immobilisation) {
        if (immobilisation.getDateMiseEnService() == null || immobilisation.getDureeGarantieMois() == null) {
            return false;
        }
        LocalDate finGarantie = immobilisation.getDateMiseEnService()
                .plusMonths(immobilisation.getDureeGarantieMois());

        return LocalDate.now().isBefore(finGarantie) || LocalDate.now().isEqual(finGarantie);
    }
    private Integer calculerAgeEnMois(Immobilisation immobilisation) {
        if (immobilisation.getDateAcquisition() == null) {
            return null;
        }
        Period period = Period.between(immobilisation.getDateAcquisition(), LocalDate.now());
        return period.getYears() * 12 + period.getMonths();
    }
}
