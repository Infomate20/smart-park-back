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
import smartPark.smart_park.mapper.AgenceMapper;
import smartPark.smart_park.models.dto.request.AgenceRequestDto;
import smartPark.smart_park.models.dto.response.AgenceResponseDto;
import smartPark.smart_park.models.entity.Agence;
import smartPark.smart_park.repository.AgenceRepository;
import smartPark.smart_park.services.AgenceService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AgenceServiceImpl implements AgenceService {

    @Autowired
    private final AgenceRepository agenceRepository;
    @Autowired
    private final AgenceMapper agenceMapper;

    @Override
    public AgenceResponseDto creerAgence(AgenceRequestDto requestDto) {
        log.info("Création d'une nouvelle agence avec le code: {}", requestDto.getCode());

        // Vérifier l'unicité du code
        if (agenceRepository.existsByCode(requestDto.getCode().toUpperCase())) {
            throw new BusinessException("Une agence avec ce code existe déjà");
        }

        // Vérifier l'unicité du nom
        if (agenceRepository.existsByNom(requestDto.getNom())) {
            throw new BusinessException("Une agence avec ce nom existe déjà");
        }

        // Vérifier l'unicité de l'email si fourni
        if (requestDto.getEmail() != null && !requestDto.getEmail().trim().isEmpty() &&
                agenceRepository.existsByEmail(requestDto.getEmail())) {
            throw new BusinessException("Une agence avec cet email existe déjà");
        }

        Agence agence = agenceMapper.toEntity(requestDto);
        Agence agenceEnregistree = agenceRepository.save(agence);

        log.info("Agence créée avec succès avec l'ID: {} et code: {}",
                agenceEnregistree.getId(), agenceEnregistree.getCode());
        return agenceMapper.toResponseDto(agenceEnregistree);
    }

    @Override
    @Transactional(readOnly = true)
    public AgenceResponseDto obtenirAgenceParId(Long id) {
        log.info("Recherche de l'agence avec l'ID: {}", id);

        Agence agence = agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence non trouvée avec l'ID: " + id));

        return agenceMapper.toResponseDto(agence);
    }

    @Override
    @Transactional(readOnly = true)
    public AgenceResponseDto obtenirAgenceParCode(String code) {
        log.info("Recherche de l'agence avec le code: {}", code);

        Agence agence = agenceRepository.findByCode(code.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Agence non trouvée avec le code: " + code));

        return agenceMapper.toResponseDto(agence);
    }

    @Override
    @Transactional(readOnly = true)
    public AgenceResponseDto obtenirAgenceParNom(String nom) {
        log.info("Recherche de l'agence avec le nom: {}", nom);

        Agence agence = agenceRepository.findByNom(nom)
                .orElseThrow(() -> new ResourceNotFoundException("Agence non trouvée avec le nom: " + nom));

        return agenceMapper.toResponseDto(agence);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgenceResponseDto> obtenirToutesLesAgences() {
        log.info("Récupération de toutes les agences");

        List<Agence> agences = agenceRepository.findAll();
        return agenceMapper.toResponseDtoList(agences);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgenceResponseDto> obtenirAgencesActives() {
        log.info("Récupération des agences actives");

        List<Agence> agences = agenceRepository.findByActifTrue();
        return agenceMapper.toResponseDtoList(agences);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgenceResponseDto> obtenirAgencesAvecPagination(String searchTerm, Boolean actif,Pageable pageable) {
        log.info("Récupération des agences avec pagination: search {},actif {},page {}, taille {}",
               searchTerm,actif, pageable.getPageNumber(), pageable.getPageSize());
        String effectiveSearchTerm = (searchTerm != null && !searchTerm.trim().isEmpty()) ? searchTerm : null;

        Page<Agence> agences = agenceRepository.findWithFilters(effectiveSearchTerm,actif,pageable);
        return agences.map(agenceMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgenceResponseDto> obtenirAgencesAvecPaginationA(Pageable pageable) {
        log.info("Récupération des agences avec pagination:,page {}, taille {}",
                 pageable.getPageNumber(), pageable.getPageSize());

        Page<Agence> agences = agenceRepository.findByActifTrue(pageable);
        return agences.map(agenceMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgenceResponseDto> rechercherAgences(String searchTerm, Pageable pageable) {
        log.info("Recherche d'agences avec le terme: {}", searchTerm);

        Page<Agence> agences = agenceRepository.findBySearchTerm(searchTerm, pageable);
        return agences.map(agenceMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgenceResponseDto> obtenirAgencesParVille(String ville) {
        log.info("Récupération des agences pour la ville: {}", ville);

        List<Agence> agences = agenceRepository.findByVille(ville);
        return agenceMapper.toResponseDtoList(agences);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgenceResponseDto> obtenirAgencesParPays(String pays) {
        log.info("Récupération des agences pour le pays: {}", pays);

        List<Agence> agences = agenceRepository.findByPays(pays);
        return agenceMapper.toResponseDtoList(agences);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgenceResponseDto> obtenirAgencesParVilleAvecPagination(String ville, Pageable pageable) {
        log.info("Récupération des agences pour la ville: {} avec pagination", ville);

        Page<Agence> agences = agenceRepository.findByVilleAndActifTrue(ville, pageable);
        return agences.map(agenceMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgenceResponseDto> obtenirAgencesParPaysAvecPagination(String pays, Pageable pageable) {
        log.info("Récupération des agences pour le pays: {} avec pagination", pays);

        Page<Agence> agences = agenceRepository.findByPaysAndActifTrue(pays, pageable);
        return agences.map(agenceMapper::toResponseDto);
    }

    @Override
    public AgenceResponseDto modifierAgence(Long id, AgenceRequestDto requestDto) {
        log.info("Modification de l'agence avec l'ID: {}", id);

        Agence agence = agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence non trouvée avec l'ID: " + id));

        // Vérifier l'unicité du code (exclure l'agence actuelle)
        if (requestDto.getCode() != null &&
                agenceRepository.existsByCodeAndIdNot(requestDto.getCode().toUpperCase(), id)) {
            throw new BusinessException("Une autre agence avec ce code existe déjà");
        }

        // Vérifier l'unicité du nom (exclure l'agence actuelle)
        if (requestDto.getNom() != null &&
                agenceRepository.existsByNomAndIdNot(requestDto.getNom(), id)) {
            throw new BusinessException("Une autre agence avec ce nom existe déjà");
        }

        // Vérifier l'unicité de l'email si fourni (exclure l'agence actuelle)
        if (requestDto.getEmail() != null && !requestDto.getEmail().trim().isEmpty() &&
                agenceRepository.existsByEmailAndIdNot(requestDto.getEmail(), id)) {
            throw new BusinessException("Une autre agence avec cet email existe déjà");
        }

        agenceMapper.updateEntityFromDto(requestDto, agence);
        Agence agenceModifiee = agenceRepository.save(agence);

        log.info("Agence modifiée avec succès avec l'ID: {}", id);
        return agenceMapper.toResponseDto(agenceModifiee);
    }

    @Override
    public void supprimerAgence(Long id) {
        log.info("Suppression de l'agence avec l'ID: {}", id);

        Agence agence = agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence non trouvée avec l'ID: " + id));

        // Vérifier si l'agence peut être supprimée
        if (!peutSupprimerAgence(id)) {
            throw new BusinessException("Impossible de supprimer cette agence car elle contient des immobilisations, utilisateurs ou transactions");
        }

        agenceRepository.delete(agence);
        log.info("Agence supprimée avec succès avec l'ID: {}", id);
    }

    @Override
    public void desactiverAgence(Long id) {
        log.info("Désactivation de l'agence avec l'ID: {}", id);

        Agence agence = agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence non trouvée avec l'ID: " + id));

        agence.setActif(false);
        agenceRepository.save(agence);

        log.info("Agence désactivée avec succès avec l'ID: {}", id);
    }

    @Override
    public void activerAgence(Long id) {
        log.info("Activation de l'agence avec l'ID: {}", id);

        Agence agence = agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence non trouvée avec l'ID: " + id));

        agence.setActif(true);
        agenceRepository.save(agence);

        log.info("Agence activée avec succès avec l'ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifierUniciteCode(String code, Long excludeId) {
        log.debug("Vérification de l'unicité du code: {} (exclude ID: {})", code, excludeId);

        if (excludeId != null) {
            return !agenceRepository.existsByCodeAndIdNot(code.toUpperCase(), excludeId);
        } else {
            return !agenceRepository.existsByCode(code.toUpperCase());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifierUniciteNom(String nom, Long excludeId) {
        log.debug("Vérification de l'unicité du nom: {} (exclude ID: {})", nom, excludeId);

        if (excludeId != null) {
            return !agenceRepository.existsByNomAndIdNot(nom, excludeId);
        } else {
            return !agenceRepository.existsByNom(nom);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifierUniciteEmail(String email, Long excludeId) {
        log.debug("Vérification de l'unicité de l'email: {} (exclude ID: {})", email, excludeId);

        if (email == null || email.trim().isEmpty()) {
            return true; // Email optionnel
        }

        if (excludeId != null) {
            return !agenceRepository.existsByEmailAndIdNot(email, excludeId);
        } else {
            return !agenceRepository.existsByEmail(email);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Long compterImmobilisationsParAgence(Long agenceId) {
        log.info("Comptage des immobilisations pour l'agence: {}", agenceId);

        return agenceRepository.countImmobilisationsActivesByAgenceId(agenceId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long compterUtilisateursParAgence(Long agenceId) {
        log.info("Comptage des utilisateurs pour l'agence: {}", agenceId);

        return agenceRepository.countUtilisateursActifsByAgenceId(agenceId);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> obtenirStatistiquesAgence(Long agenceId) {
        log.info("Récupération des statistiques pour l'agence: {}", agenceId);

        // Vérifier que l'agence existe
        if (!agenceRepository.existsById(agenceId)) {
            throw new ResourceNotFoundException("Agence non trouvée avec l'ID: " + agenceId);
        }

        Map<String, Long> statistiques = new HashMap<>();
        statistiques.put("nombreImmobilisations", compterImmobilisationsParAgence(agenceId));
        statistiques.put("nombreUtilisateurs", compterUtilisateursParAgence(agenceId));

        return statistiques;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> obtenirStatistiquesGenerales() {
        log.info("Récupération des statistiques générales des agences");

        Map<String, Object> statistiques = new HashMap<>();

        // Compter les agences
        long totalAgences = agenceRepository.count();
        long agencesActives = agenceRepository.findByActifTrue().size();

        statistiques.put("totalAgences", totalAgences);
        statistiques.put("agencesActives", agencesActives);
        statistiques.put("agencesInactives", totalAgences - agencesActives);

        // Statistiques par pays et villes
        List<String> villes = agenceRepository.findDistinctVillesActives();
        List<String> pays = agenceRepository.findDistinctPaysActifs();

        statistiques.put("nombreVilles", villes.size());
        statistiques.put("nombrePays", pays.size());
        statistiques.put("villes", villes);
        statistiques.put("pays", pays);

        return statistiques;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> obtenirVillesDisponibles() {
        log.info("Récupération de la liste des villes disponibles");

        return agenceRepository.findDistinctVillesActives();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> obtenirPaysDisponibles() {
        log.info("Récupération de la liste des pays disponibles");

        return agenceRepository.findDistinctPaysActifs();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean peutSupprimerAgence(Long agenceId) {
        log.info("Vérification si l'agence {} peut être supprimée", agenceId);

        Agence agence = agenceRepository.findById(agenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Agence non trouvée avec l'ID: " + agenceId));

        // Vérifier s'il y a des immobilisations
        boolean aDesImmobilisations = agence.getImmobilisations() != null && !agence.getImmobilisations().isEmpty();

        // Vérifier s'il y a des utilisateurs
        boolean aDesUtilisateurs = agence.getUtilisateurs() != null && !agence.getUtilisateurs().isEmpty();

        // Vérifier s'il y a des transactions
        boolean aDesTransactions = (agence.getTransactionsSource() != null && !agence.getTransactionsSource().isEmpty()) ||
                (agence.getTransactionsDestination() != null && !agence.getTransactionsDestination().isEmpty());

        boolean peutSupprimer = !aDesImmobilisations && !aDesUtilisateurs && !aDesTransactions;

        log.info("Agence {} peut être supprimée: {} (immobilisations: {}, utilisateurs: {}, transactions: {})",
                agenceId, peutSupprimer, aDesImmobilisations, aDesUtilisateurs, aDesTransactions);

        return peutSupprimer;
    }
}