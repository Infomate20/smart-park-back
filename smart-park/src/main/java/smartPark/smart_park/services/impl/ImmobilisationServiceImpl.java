package smartPark.smart_park.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartPark.smart_park.exceptions.BusinessException;
import smartPark.smart_park.exceptions.ResourceNotFoundException;
import smartPark.smart_park.mapper.ImmobilisationMapper;
import smartPark.smart_park.models.dto.request.ImmobilisationRequestDto;
import smartPark.smart_park.models.dto.response.ImmobilisationResponseDto;
import smartPark.smart_park.models.entity.Agence;
import smartPark.smart_park.models.entity.Immobilisation;
import smartPark.smart_park.models.entity.enums.EtatImmobilisation;
import smartPark.smart_park.repository.AgenceRepository;
import smartPark.smart_park.repository.ImmobilisationRepository;
import smartPark.smart_park.services.CodeGenerationService;
import smartPark.smart_park.services.ImmobilisationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ImmobilisationServiceImpl implements ImmobilisationService {
    @Autowired
    private final ImmobilisationRepository immobilisationRepository;
    @Autowired
    private final AgenceRepository agenceRepository;
    @Autowired
    private final ImmobilisationMapper immobilisationMapper;
    @Autowired
    private final CodeGenerationService codeGenerationService;

    @Override
    public ImmobilisationResponseDto creerImmobilisation(ImmobilisationRequestDto requestDto) {
        log.info("Création d'une nouvelle immobilisation avec numéro série: {}", requestDto.getNumeroSerie());

        // Vérifier l'unicité du numéro de série
        if (immobilisationRepository.existsByNumeroSerie(requestDto.getNumeroSerie())) {
            throw new BusinessException("Une immobilisation avec ce numéro de série existe déjà");
        }
        // Générer et vérifier l'unicité du code d'immobilisation
        String codeGenere = codeGenerationService.genererCodeImmobilisation(
                requestDto.getNumeroSerie(),
                requestDto.getAgenceId()
        );

        // Si le code existe déjà, ajouter un suffixe numérique
        String codeUnique = garantirUniciteCode(codeGenere);

        Immobilisation immobilisation = immobilisationMapper.toEntity(requestDto);
        immobilisation.setCodeImmobilisation(codeUnique);

        Immobilisation immobilisationEnregistree = immobilisationRepository.save(immobilisation);

        log.info("Immobilisation créée avec succès avec l'ID: {} et code: {}",
                immobilisationEnregistree.getId(), immobilisationEnregistree.getCodeImmobilisation());
        return immobilisationMapper.toResponseDto(immobilisationEnregistree);
    }

    @Override
    @Transactional(readOnly = true)
    public ImmobilisationResponseDto obtenirImmobilisationParId(Long id) {
        log.info("Recherche de l'immobilisation avec l'ID: {}", id);

        Immobilisation immobilisation = immobilisationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + id));

        return immobilisationMapper.toResponseDto(immobilisation);
    }

    @Override
    @Transactional(readOnly = true)
    public ImmobilisationResponseDto obtenirImmobilisationParCode(String codeImmobilisation) {
        log.info("Recherche de l'immobilisation avec le code: {}", codeImmobilisation);

        Immobilisation immobilisation = immobilisationRepository.findByCodeImmobilisation(codeImmobilisation)
                .orElseThrow(() -> new ResourceNotFoundException("Immobilisation non trouvée avec le code: " + codeImmobilisation));

        return immobilisationMapper.toResponseDto(immobilisation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImmobilisationResponseDto> obtenirToutesLesImmobilisations() {
        log.info("Récupération de toutes les immobilisations");

        List<Immobilisation> immobilisations = immobilisationRepository.findAll();
        return immobilisationMapper.toResponseDtoList(immobilisations);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImmobilisationResponseDto> obtenirImmobilisationsActives() {
        log.info("Récupération des immobilisations actives");

        List<Immobilisation> immobilisations = immobilisationRepository.findByActifTrue();
        return immobilisationMapper.toResponseDtoList(immobilisations);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<ImmobilisationResponseDto> obtenirImmobilisationsAvecPagination(Pageable pageable) {
        log.info("Récupération des immobilisations avec pagination: page {}, taille {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Immobilisation> immobilisations = immobilisationRepository.findByActifTrue(pageable);
        return immobilisations.map(immobilisationMapper::toResponseDto);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<ImmobilisationResponseDto> obtenirImmobilisationAvecPagination(
            String searchTerm, Boolean actif, Pageable pageable) {
        log.info("Récupération paginée d'immobilisations avec terme: '{}' et statut: {}", searchTerm, actif);

        String effectiveSearchTerm = (searchTerm != null && !searchTerm.trim().isEmpty()) ? searchTerm : null;

        // On appelle la nouvelle méthode puissante du repository
        Page<Immobilisation> immobilisations = immobilisationRepository.findWithFilters(
                effectiveSearchTerm,
                actif,
                pageable
        );

        return immobilisations.map(immobilisationMapper::toResponseDto); // La conversion en DTO ne change pas
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImmobilisationResponseDto> rechercherImmobilisations(String searchTerm, Pageable pageable) {
        log.info("Recherche d'immobilisations avec le terme: {}", searchTerm);

        Page<Immobilisation> immobilisations = immobilisationRepository.findBySearchTerm(searchTerm, pageable);
        return immobilisations.map(immobilisationMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImmobilisationResponseDto> obtenirImmobilisationsParCategorie(Long categorieId, Pageable pageable) {
        log.info("Récupération des immobilisations pour la catégorie: {}", categorieId);

        Page<Immobilisation> immobilisations = immobilisationRepository.findByCategorieIdAndActifTrue(categorieId, pageable);
        return immobilisations.map(immobilisationMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImmobilisationResponseDto> obtenirImmobilisationsParAgence(Long agenceId, Pageable pageable) {
        log.info("Récupération des immobilisations pour l'agence: {}", agenceId);

        Page<Immobilisation> immobilisations = immobilisationRepository.findByAgenceIdAndActifTrue(agenceId, pageable);
        return immobilisations.map(immobilisationMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImmobilisationResponseDto> obtenirImmobilisationsSansAgence(Pageable pageable) {
        log.info("Récupération des immobilisations sans agence");

        Page<Immobilisation> immobilisations = immobilisationRepository.findByAgenceIsNullAndActifTrue(pageable);
        return immobilisations.map(immobilisationMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImmobilisationResponseDto> obtenirImmobilisationsParEtat(EtatImmobilisation etat, Pageable pageable) {
        log.info("Récupération des immobilisations avec l'état: {}", etat);

        Page<Immobilisation> immobilisations = immobilisationRepository.findByEtatAndActifTrue(etat, pageable);
        return immobilisations.map(immobilisationMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImmobilisationResponseDto> obtenirImmobilisationsParPeriodeAcquisition(LocalDate dateDebut, LocalDate dateFin) {
        log.info("Récupération des immobilisations acquises entre {} et {}", dateDebut, dateFin);

        if (dateDebut.isAfter(dateFin)) {
            throw new BusinessException("La date de début doit être antérieure à la date de fin");
        }

        List<Immobilisation> immobilisations = immobilisationRepository.findByDateAcquisitionBetween(dateDebut, dateFin);
        return immobilisationMapper.toResponseDtoList(immobilisations);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImmobilisationResponseDto> obtenirImmobilisationsParPlageGPrix(BigDecimal prixMin, BigDecimal prixMax, Pageable pageable) {
        log.info("Récupération des immobilisations avec prix entre {} et {}", prixMin, prixMax);

        if (prixMin.compareTo(prixMax) > 0) {
            throw new BusinessException("Le prix minimum doit être inférieur au prix maximum");
        }

        Page<Immobilisation> immobilisations = immobilisationRepository.findByPrixAcquisitionBetween(prixMin, prixMax, pageable);
        return immobilisations.map(immobilisationMapper::toResponseDto);
    }

    @Override
    public ImmobilisationResponseDto modifierImmobilisation(Long id, ImmobilisationRequestDto requestDto) {
        log.info("Modification de l'immobilisation avec l'ID: {}", id);

        Immobilisation immobilisation = immobilisationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + id));

        // Vérifier l'unicité du numéro de série (exclure l'immobilisation actuelle)
        if (requestDto.getNumeroSerie() != null &&
                immobilisationRepository.existsByNumeroSerieAndIdNot(requestDto.getNumeroSerie(), id)) {
            throw new BusinessException("Une autre immobilisation avec ce numéro de série existe déjà");
        }

        String ancienCode = immobilisation.getCodeImmobilisation();
        immobilisationMapper.updateEntityFromDto(requestDto, immobilisation);

        // Si le code a changé, vérifier son unicité
        if (!ancienCode.equals(immobilisation.getCodeImmobilisation())) {
            String codeUnique = garantirUniciteCode(immobilisation.getCodeImmobilisation());
            immobilisation.setCodeImmobilisation(codeUnique);
        }

        Immobilisation immobilisationModifiee = immobilisationRepository.save(immobilisation);

        log.info("Immobilisation modifiée avec succès avec l'ID: {}", id);
        return immobilisationMapper.toResponseDto(immobilisationModifiee);
    }

    @Override
    public void supprimerImmobilisation(Long id) {
        log.info("Suppression de l'immobilisation avec l'ID: {}", id);

        Immobilisation immobilisation = immobilisationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + id));

        // Vérifier s'il y a des interventions ou transactions associées
        if ((immobilisation.getInterventions() != null && !immobilisation.getInterventions().isEmpty()) ||
                (immobilisation.getTransactions() != null && !immobilisation.getTransactions().isEmpty())) {
            throw new BusinessException("Impossible de supprimer cette immobilisation car elle a des interventions ou transactions associées");
        }

        immobilisationRepository.delete(immobilisation);
        log.info("Immobilisation supprimée avec succès avec l'ID: {}", id);
    }

    @Override
    public void desactiverImmobilisation(Long id) {
        log.info("Désactivation de l'immobilisation avec l'ID: {}", id);

        Immobilisation immobilisation = immobilisationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + id));

        immobilisation.setActif(false);
        immobilisationRepository.save(immobilisation);

        log.info("Immobilisation désactivée avec succès avec l'ID: {}", id);
    }

    @Override
    public void activerImmobilisation(Long id) {
        log.info("Activation de l'immobilisation avec l'ID: {}", id);

        Immobilisation immobilisation = immobilisationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + id));

        immobilisation.setActif(true);
        immobilisationRepository.save(immobilisation);

        log.info("Immobilisation activée avec succès avec l'ID: {}", id);
    }

    @Override
    public void changerEtatImmobilisation(Long id, EtatImmobilisation nouvelEtat) {
        log.info("Changement d'état de l'immobilisation {} vers {}", id, nouvelEtat);

        Immobilisation immobilisation = immobilisationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + id));

        EtatImmobilisation ancienEtat = immobilisation.getEtat();

        // Validation des transitions d'état
        validerTransitionEtat(ancienEtat, nouvelEtat);

        immobilisation.setEtat(nouvelEtat);
        immobilisationRepository.save(immobilisation);

        log.info("État de l'immobilisation {} changé de {} vers {}", id, ancienEtat, nouvelEtat);
    }

    @Override
    public void transfererVersAgence(Long immobilisationId, Long agenceId) {
        log.info("Transfert de l'immobilisation {} vers l'agence {}", immobilisationId, agenceId);

        Immobilisation immobilisation = immobilisationRepository.findById(immobilisationId)
                .orElseThrow(() -> new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + immobilisationId));

        Agence nouvelleAgence = agenceRepository.findById(agenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Agence non trouvée avec l'ID: " + agenceId));

        // Vérifier que l'immobilisation n'est pas déjà dans cette agence
        if (immobilisation.getAgence() != null && immobilisation.getAgence().getId().equals(agenceId)) {
            throw new BusinessException("L'immobilisation est déjà affectée à cette agence");
        }

        immobilisation.setAgence(nouvelleAgence);

        // Régénérer le code d'immobilisation avec la nouvelle agence
        String nouveauCode = codeGenerationService.genererCodeImmobilisation(
                immobilisation.getNumeroSerie(),
                agenceId
        );
        String codeUnique = garantirUniciteCode(nouveauCode);
        immobilisation.setCodeImmobilisation(codeUnique);

        immobilisationRepository.save(immobilisation);

        log.info("Immobilisation {} transférée vers l'agence {} avec nouveau code: {}",
                immobilisationId, agenceId, codeUnique);
    }

    @Override
    public void retirerDeAgence(Long immobilisationId) {
        log.info("Retrait de l'immobilisation {} de son agence", immobilisationId);

        Immobilisation immobilisation = immobilisationRepository.findById(immobilisationId)
                .orElseThrow(() -> new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + immobilisationId));

        if (immobilisation.getAgence() == null) {
            throw new BusinessException("L'immobilisation n'est affectée à aucune agence");
        }

        immobilisation.setAgence(null);

        // Régénérer le code d'immobilisation sans agence
        String nouveauCode = codeGenerationService.genererCodeImmobilisationSansAgence(immobilisation.getNumeroSerie());
        String codeUnique = garantirUniciteCode(nouveauCode);
        immobilisation.setCodeImmobilisation(codeUnique);

        immobilisationRepository.save(immobilisation);

        log.info("Immobilisation {} retirée de son agence avec nouveau code: {}", immobilisationId, codeUnique);
    }

    @Override
    @Transactional(readOnly = true)
    public Long compterImmobilisationsParCategorie(Long categorieId) {
        log.info("Comptage des immobilisations pour la catégorie: {}", categorieId);

        return immobilisationRepository.countByCategorieIdAndActifTrue(categorieId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long compterImmobilisationsParAgence(Long agenceId) {
        log.info("Comptage des immobilisations pour l'agence: {}", agenceId);

        return immobilisationRepository.countByAgenceIdAndActifTrue(agenceId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long compterImmobilisationsParEtat(EtatImmobilisation etat) {
        log.info("Comptage des immobilisations avec l'état: {}", etat);

        return immobilisationRepository.countByEtatAndActifTrue(etat);
    }

    // ===== MÉTHODES PRIVÉES UTILITAIRES =====

    private String garantirUniciteCode(String codeBase) {
        String codeUnique = codeBase;
        int compteur = 1;

        while (immobilisationRepository.existsByCodeImmobilisation(codeUnique)) {
            codeUnique = String.format("%s-%03d", codeBase, compteur);
            compteur++;

            // Sécurité pour éviter une boucle infinie
            if (compteur > 999) {
                throw new BusinessException("Impossible de générer un code unique pour l'immobilisation");
            }
        }

        return codeUnique;
    }

    private void validerTransitionEtat(EtatImmobilisation etatActuel, EtatImmobilisation nouvelEtat) {
        if (etatActuel == nouvelEtat) {
            throw new BusinessException("L'immobilisation est déjà dans l'état: " + nouvelEtat.getLibelle());
        }

        // Règles métier pour les transitions d'état
        switch (etatActuel) {
            case EN_SERVICE:
                // Depuis EN_SERVICE, on peut aller vers tous les autres états
                break;

            case EN_PANNE:
                // Depuis EN_PANNE, on peut aller vers EN_REPARATION, EN_SERVICE ou MISE_AU_REBUS
                if (nouvelEtat == EtatImmobilisation.EN_PANNE) {
                    throw new BusinessException("Transition d'état non autorisée");
                }
                break;

            case EN_REPARATION:
                // Depuis EN_REPARATION, on peut aller vers EN_SERVICE, EN_PANNE ou MISE_AU_REBUS
                if (nouvelEtat == EtatImmobilisation.EN_REPARATION) {
                    throw new BusinessException("Transition d'état non autorisée");
                }
                break;

            case MISE_AU_REBUS:
                // Depuis MISE_AU_REBUS, on ne peut plus changer d'état (irréversible)
                throw new BusinessException("Une immobilisation mise au rebut ne peut pas changer d'état");

            default:
                throw new BusinessException("État d'immobilisation non reconnu: " + etatActuel);
        }
    }
}