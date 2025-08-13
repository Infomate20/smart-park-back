package smartPark.smart_park.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartPark.smart_park.exceptions.BusinessException;
import smartPark.smart_park.exceptions.ResourceNotFoundException;
import smartPark.smart_park.exceptions.UnauthorizedException;
import smartPark.smart_park.mapper.UtilisateurMapper;
import smartPark.smart_park.models.dto.request.ChangementMotDePasseRequestDto;
import smartPark.smart_park.models.dto.request.ConnexionRequestDto;
import smartPark.smart_park.models.dto.request.UtilisateurRequestDto;
import smartPark.smart_park.models.dto.response.UtilisateurResponseDto;
import smartPark.smart_park.models.entity.Agence;
import smartPark.smart_park.models.entity.Utilisateur;
import smartPark.smart_park.models.entity.enums.Role;
import smartPark.smart_park.repository.AgenceRepository;
import smartPark.smart_park.repository.UtilisateurRepository;
import smartPark.smart_park.services.UtilisateurService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {
    @Autowired
    private final UtilisateurRepository utilisateurRepository;
    @Autowired
    private final AgenceRepository agenceRepository;
    @Autowired
    private final UtilisateurMapper utilisateurMapper;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    private static final int MAX_TENTATIVES_CONNEXION = 5;

    @Override
    public UtilisateurResponseDto creerUtilisateur(UtilisateurRequestDto requestDto) {
        log.info("Création d'un nouvel utilisateur avec le nom d'utilisateur: {}", requestDto.getNomUtilisateur());

        // Vérifier l'unicité du nom d'utilisateur
        if (utilisateurRepository.existsByNomUtilisateur(requestDto.getNomUtilisateur().toLowerCase())) {
            throw new BusinessException("Un utilisateur avec ce nom d'utilisateur existe déjà");
        }

        // Vérifier l'unicité de l'email si fourni
        if (requestDto.getEmail() != null && !requestDto.getEmail().trim().isEmpty() &&
                utilisateurRepository.existsByEmail(requestDto.getEmail())) {
            throw new BusinessException("Un utilisateur avec cet email existe déjà");
        }

        // Vérifier l'unicité du matricule si fourni
        if (requestDto.getMatricule() != null && !requestDto.getMatricule().trim().isEmpty() &&
                utilisateurRepository.existsByMatricule(requestDto.getMatricule())) {
            throw new BusinessException("Un utilisateur avec ce matricule existe déjà");
        }

        // Vérifier que le mot de passe est fourni pour la création
        if (requestDto.getMotDePasse() == null || requestDto.getMotDePasse().trim().isEmpty()) {
            throw new BusinessException("Le mot de passe est obligatoire lors de la création");
        }

        Utilisateur utilisateur = utilisateurMapper.toEntity(requestDto);
        Utilisateur utilisateurEnregistre = utilisateurRepository.save(utilisateur);

        log.info("Utilisateur créé avec succès avec l'ID: {} et nom d'utilisateur: {}",
                utilisateurEnregistre.getId(), utilisateurEnregistre.getNomUtilisateur());
        return utilisateurMapper.toResponseDto(utilisateurEnregistre);
    }

    @Override
    @Transactional(readOnly = true)
    public UtilisateurResponseDto obtenirUtilisateurParId(Long id) {
        log.info("Recherche de l'utilisateur avec l'ID: {}", id);

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));

        return utilisateurMapper.toResponseDto(utilisateur);
    }

    @Override
    @Transactional(readOnly = true)
    public UtilisateurResponseDto obtenirUtilisateurParNomUtilisateur(String nomUtilisateur) {
        log.info("Recherche de l'utilisateur avec le nom d'utilisateur: {}", nomUtilisateur);

        Utilisateur utilisateur = utilisateurRepository.findByNomUtilisateur(nomUtilisateur.toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec le nom d'utilisateur: " + nomUtilisateur));

        return utilisateurMapper.toResponseDto(utilisateur);
    }

    @Override
    @Transactional(readOnly = true)
    public UtilisateurResponseDto obtenirUtilisateurParEmail(String email) {
        log.info("Recherche de l'utilisateur avec l'email: {}", email);

        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'email: " + email));

        return utilisateurMapper.toResponseDto(utilisateur);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UtilisateurResponseDto> obtenirTousLesUtilisateurs() {
        log.info("Récupération de tous les utilisateurs");

        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        return utilisateurMapper.toResponseDtoList(utilisateurs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UtilisateurResponseDto> obtenirUtilisateursActifs() {
        log.info("Récupération des utilisateurs actifs");

        List<Utilisateur> utilisateurs = utilisateurRepository.findByActifTrue();
        return utilisateurMapper.toResponseDtoList(utilisateurs);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UtilisateurResponseDto> obtenirUtilisateursAvecPagination( String searchTerm, Boolean actif, Pageable pageable) {
        log.info("Récupération des utilisateurs par filtre avec pagination: term{} ,filtre{} ,page {}, taille {}",searchTerm, actif,
                pageable.getPageNumber(), pageable.getPageSize());
        String effectiveSearchTerm = (searchTerm != null && !searchTerm.trim().isEmpty()) ? searchTerm : null;
        Page<Utilisateur> utilisateurs = utilisateurRepository.findWithFilter(effectiveSearchTerm, actif, pageable);
        return utilisateurs.map(utilisateurMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UtilisateurResponseDto> rechercherUtilisateurs(String searchTerm, Pageable pageable) {
        log.info("Recherche d'utilisateurs avec le terme: {}", searchTerm);

        Page<Utilisateur> utilisateurs = utilisateurRepository.findBySearchTerm(searchTerm, pageable);
        return utilisateurs.map(utilisateurMapper::toResponseDto);
    }

    @Override
    public UtilisateurResponseDto modifierUtilisateur(Long id, UtilisateurRequestDto requestDto) {
        log.info("Modification de l'utilisateur avec l'ID: {}", id);

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));

        // Vérifier l'unicité du nom d'utilisateur (exclure l'utilisateur actuel)
        if (requestDto.getNomUtilisateur() != null &&
                utilisateurRepository.existsByNomUtilisateurAndIdNot(requestDto.getNomUtilisateur().toLowerCase(), id)) {
            throw new BusinessException("Un autre utilisateur avec ce nom d'utilisateur existe déjà");
        }

        // Vérifier l'unicité de l'email si fourni (exclure l'utilisateur actuel)
        if (requestDto.getEmail() != null && !requestDto.getEmail().trim().isEmpty() &&
                utilisateurRepository.existsByEmailAndIdNot(requestDto.getEmail(), id)) {
            throw new BusinessException("Un autre utilisateur avec cet email existe déjà");
        }

        // Vérifier l'unicité du matricule si fourni (exclure l'utilisateur actuel)
        if (requestDto.getMatricule() != null && !requestDto.getMatricule().trim().isEmpty() &&
                utilisateurRepository.existsByMatriculeAndIdNot(requestDto.getMatricule(), id)) {
            throw new BusinessException("Un autre utilisateur avec ce matricule existe déjà");
        }

        utilisateurMapper.updateEntityFromDto(requestDto, utilisateur);
        Utilisateur utilisateurModifie = utilisateurRepository.save(utilisateur);

        log.info("Utilisateur modifié avec succès avec l'ID: {}", id);
        return utilisateurMapper.toResponseDto(utilisateurModifie);
    }

    @Override
    public void supprimerUtilisateur(Long id) {
        log.info("Suppression de l'utilisateur avec l'ID: {}", id);

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));

        // Vérifier si l'utilisateur peut être supprimé
        if (!peutSupprimerUtilisateur(id)) {
            throw new BusinessException("Impossible de supprimer cet utilisateur car il a des interventions ou transactions associées");
        }

        utilisateurRepository.delete(utilisateur);
        log.info("Utilisateur supprimé avec succès avec l'ID: {}", id);
    }

    @Override
    public void desactiverUtilisateur(Long id) {
        log.info("Désactivation de l'utilisateur avec l'ID: {}", id);

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));

        utilisateur.setActif(false);
        utilisateurRepository.save(utilisateur);

        log.info("Utilisateur désactivé avec succès avec l'ID: {}", id);
    }

    @Override
    public void activerUtilisateur(Long id) {
        log.info("Activation de l'utilisateur avec l'ID: {}", id);

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));

        utilisateur.setActif(true);
        utilisateurRepository.save(utilisateur);

        log.info("Utilisateur activé avec succès avec l'ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UtilisateurResponseDto> obtenirUtilisateursParRole(Role role, Pageable pageable) {
        log.info("Récupération des utilisateurs avec le rôle: {}", role);

        Page<Utilisateur> utilisateurs = utilisateurRepository.findByRoleAndActifTrue(role, pageable);
        return utilisateurs.map(utilisateurMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UtilisateurResponseDto> obtenirUtilisateursParAgence(Long agenceId, Pageable pageable) {
        log.info("Récupération des utilisateurs pour l'agence: {}", agenceId);

        Page<Utilisateur> utilisateurs = utilisateurRepository.findByAgenceIdAndActifTrue(agenceId, pageable);
        return utilisateurs.map(utilisateurMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UtilisateurResponseDto> obtenirUtilisateursSansAgence(Pageable pageable) {
        log.info("Récupération des utilisateurs sans agence");

        Page<Utilisateur> utilisateurs = utilisateurRepository.findByAgenceIsNullAndActifTrue(pageable);
        return utilisateurs.map(utilisateurMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UtilisateurResponseDto> obtenirUtilisateursVerrouilles(Pageable pageable) {
        log.info("Récupération des utilisateurs verrouillés");

        Page<Utilisateur> utilisateurs = utilisateurRepository.findByCompteVerrouilleTrue(pageable);
        return utilisateurs.map(utilisateurMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UtilisateurResponseDto> obtenirUtilisateursPremiereConnexion() {
        log.info("Récupération des utilisateurs en première connexion");

        List<Utilisateur> utilisateurs = utilisateurRepository.findByPremiereConnexionTrue();
        return utilisateurMapper.toResponseDtoList(utilisateurs);
    }

    @Override
    public void changerMotDePasse(Long id, ChangementMotDePasseRequestDto requestDto) {
        log.info("Changement de mot de passe pour l'utilisateur: {}", id);

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));

        // Vérifier l'ancien mot de passe
        if (!passwordEncoder.matches(requestDto.getAncienMotDePasse(), utilisateur.getMotDePasse())) {
            throw new BusinessException("L'ancien mot de passe est incorrect");
        }

        // Vérifier que la confirmation correspond
        if (!requestDto.getNouveauMotDePasse().equals(requestDto.getConfirmationMotDePasse())) {
            throw new BusinessException("La confirmation du mot de passe ne correspond pas");
        }

        // Vérifier que le nouveau mot de passe est différent de l'ancien
        if (passwordEncoder.matches(requestDto.getNouveauMotDePasse(), utilisateur.getMotDePasse())) {
            throw new BusinessException("Le nouveau mot de passe doit être différent de l'ancien");
        }

        utilisateur.setMotDePasse(passwordEncoder.encode(requestDto.getNouveauMotDePasse()));
        utilisateur.setMotDePasseExpire(false);
        utilisateur.setPremiereConnexion(false);
        utilisateurRepository.save(utilisateur);

        log.info("Mot de passe changé avec succès pour l'utilisateur: {}", id);
    }

    @Override
    public void resetMotDePasse(Long id, String nouveauMotDePasse) {
        log.info("Reset du mot de passe pour l'utilisateur: {}", id);

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));

        utilisateur.setMotDePasse(passwordEncoder.encode(nouveauMotDePasse));
        utilisateur.setMotDePasseExpire(true); // Forcer le changement à la prochaine connexion
        utilisateur.setTentativesConnexionEchouees(0);
        utilisateur.setCompteVerrouille(false);
        utilisateurRepository.save(utilisateur);

        log.info("Mot de passe reseté avec succès pour l'utilisateur: {}", id);
    }

    @Override
    public void marquerMotDePasseExpire(Long id) {
        log.info("Marquage du mot de passe comme expiré pour l'utilisateur: {}", id);

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));

        utilisateur.setMotDePasseExpire(true);
        utilisateurRepository.save(utilisateur);

        log.info("Mot de passe marqué comme expiré pour l'utilisateur: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public UtilisateurResponseDto authentifier(ConnexionRequestDto requestDto) {
        log.info("Tentative d'authentification pour: {}", requestDto.getNomUtilisateur());

        Utilisateur utilisateur = utilisateurRepository.findByNomUtilisateur(requestDto.getNomUtilisateur().toLowerCase())
                .orElseThrow(() -> new UnauthorizedException("Nom d'utilisateur ou mot de passe incorrect"));

        // Vérifier si le compte est actif
        if (!utilisateur.getActif()) {
            throw new UnauthorizedException("Compte désactivé");
        }

        // Vérifier si le compte est verrouillé
        if (utilisateur.getCompteVerrouille()) {
            throw new UnauthorizedException("Compte verrouillé");
        }

        // Vérifier le mot de passe
        if (!passwordEncoder.matches(requestDto.getMotDePasse(), utilisateur.getMotDePasse())) {
            // Incrémenter les tentatives échouées
            incrementerTentativesEchouees(utilisateur.getId());
            throw new UnauthorizedException("Nom d'utilisateur ou mot de passe incorrect");
        }

        // Authentification réussie
        resetTentativesEchouees(utilisateur.getId());
        enregistrerConnexion(utilisateur.getId());

        log.info("Authentification réussie pour: {}", requestDto.getNomUtilisateur());
        return utilisateurMapper.toResponseDto(utilisateur);
    }

    @Override
    public void enregistrerConnexion(Long id) {
        log.debug("Enregistrement de la connexion pour l'utilisateur: {}", id);
        utilisateurRepository.updateDerniereConnexion(id, LocalDateTime.now());
    }

    @Override
    public void incrementerTentativesEchouees(Long id) {
        log.debug("Incrémentation des tentatives échouées pour l'utilisateur: {}", id);

        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));

        int nouvelleTentatives = utilisateur.getTentativesConnexionEchouees() + 1;
        utilisateurRepository.updateTentativesConnexionEchouees(id, nouvelleTentatives);

        // Verrouiller le compte si trop de tentatives
        if (nouvelleTentatives >= MAX_TENTATIVES_CONNEXION) {
            verrouillerCompte(id);
            log.warn("Compte verrouillé pour l'utilisateur {} après {} tentatives échouées", id, nouvelleTentatives);
        }
    }

    @Override
    public void resetTentativesEchouees(Long id) {
        log.debug("Reset des tentatives échouées pour l'utilisateur: {}", id);
        utilisateurRepository.updateTentativesConnexionEchouees(id, 0);
    }

    @Override
    public void verrouillerCompte(Long id) {
        log.info("Verrouillage du compte pour l'utilisateur: {}", id);
        utilisateurRepository.updateCompteVerrouille(id, true);
    }

    @Override
    public void deverrouillerCompte(Long id) {
        log.info("Déverrouillage du compte pour l'utilisateur: {}", id);
        utilisateurRepository.updateCompteVerrouille(id, false);
        resetTentativesEchouees(id);
    }

    @Override
    public void marquerPremiereConnexionTerminee(Long id) {
        log.info("Marquage de la première connexion comme terminée pour l'utilisateur: {}", id);
        utilisateurRepository.marquerPremiereConnexionTerminee(id);
    }

    @Override
    public void transfererVersAgence(Long utilisateurId, Long agenceId) {
        log.info("Transfert de l'utilisateur {} vers l'agence {}", utilisateurId, agenceId);

        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + utilisateurId));

        Agence agence = agenceRepository.findById(agenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Agence non trouvée avec l'ID: " + agenceId));

        utilisateur.setAgence(agence);
        utilisateurRepository.save(utilisateur);

        log.info("Utilisateur {} transféré vers l'agence {}", utilisateurId, agenceId);
    }

    @Override
    public void retirerDeAgence(Long utilisateurId) {
        log.info("Retrait de l'utilisateur {} de son agence", utilisateurId);

        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + utilisateurId));

        utilisateur.setAgence(null);
        utilisateurRepository.save(utilisateur);

        log.info("Utilisateur {} retiré de son agence", utilisateurId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifierUniciteNomUtilisateur(String nomUtilisateur, Long excludeId) {
        if (excludeId != null) {
            return !utilisateurRepository.existsByNomUtilisateurAndIdNot(nomUtilisateur.toLowerCase(), excludeId);
        } else {
            return !utilisateurRepository.existsByNomUtilisateur(nomUtilisateur.toLowerCase());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifierUniciteEmail(String email, Long excludeId) {
        if (email == null || email.trim().isEmpty()) {
            return true; // Email optionnel
        }

        if (excludeId != null) {
            return !utilisateurRepository.existsByEmailAndIdNot(email, excludeId);
        } else {
            return !utilisateurRepository.existsByEmail(email);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifierUniciteMatricule(String matricule, Long excludeId) {
        if (matricule == null || matricule.trim().isEmpty()) {
            return true; // Matricule optionnel
        }

        if (excludeId != null) {
            return !utilisateurRepository.existsByMatriculeAndIdNot(matricule, excludeId);
        } else {
            return !utilisateurRepository.existsByMatricule(matricule);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Long compterUtilisateursParRole(Role role) {
        return utilisateurRepository.countByRoleAndActifTrue(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Long compterUtilisateursParAgence(Long agenceId) {
        return utilisateurRepository.countByAgenceIdAndActifTrue(agenceId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long compterUtilisateursVerrouilles() {
        return utilisateurRepository.countByCompteVerrouilleTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Long compterUtilisateursPremiereConnexion() {
        return utilisateurRepository.countByPremiereConnexionTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> obtenirStatistiquesUtilisateurs() {
        Map<String, Long> statistiques = new HashMap<>();

        long totalUtilisateurs = utilisateurRepository.count();
        long utilisateursActifs = utilisateurRepository.findByActifTrue().size();

        statistiques.put("total", totalUtilisateurs);
        statistiques.put("actifs", utilisateursActifs);
        statistiques.put("inactifs", totalUtilisateurs - utilisateursActifs);
        statistiques.put("admins", compterUtilisateursParRole(Role.ADMIN));
        statistiques.put("techniciens", compterUtilisateursParRole(Role.TECHNICIEN));
        statistiques.put("agents", compterUtilisateursParRole(Role.AGENT));
        statistiques.put("verrouilles", compterUtilisateursVerrouilles());
        statistiques.put("premiereConnexion", compterUtilisateursPremiereConnexion());

        return statistiques;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UtilisateurResponseDto> obtenirUtilisateursInactifsSince(LocalDateTime dateLimit) {
        List<Utilisateur> utilisateurs = utilisateurRepository.findUtilisateursInactifsSince(dateLimit);
        return utilisateurMapper.toResponseDtoList(utilisateurs);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean peutSupprimerUtilisateur(Long utilisateurId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + utilisateurId));

        // Vérifier s'il y a des interventions ou transactions associées
        boolean aDesInterventions = utilisateur.getInterventions() != null && !utilisateur.getInterventions().isEmpty();
        boolean aDesTransactions = utilisateur.getTransactionsCreees() != null && !utilisateur.getTransactionsCreees().isEmpty();

        return !aDesInterventions && !aDesTransactions;
    }
}