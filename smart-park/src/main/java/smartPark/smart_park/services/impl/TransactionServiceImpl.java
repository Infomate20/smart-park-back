package smartPark.smart_park.services.impl;

import smartPark.smart_park.exceptions.BusinessException;
import smartPark.smart_park.exceptions.ForbiddenException;
import smartPark.smart_park.exceptions.ValidationException;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartPark.smart_park.exceptions.ResourceNotFoundException;
import smartPark.smart_park.mapper.TransactionMapper;
import smartPark.smart_park.models.dto.request.TransactionRequestDto;
import smartPark.smart_park.models.dto.request.TransactionUpdateDto;
import smartPark.smart_park.models.dto.request.TransactionValidationDto;
import smartPark.smart_park.models.dto.response.TransactionResponseDto;
import smartPark.smart_park.models.entity.Agence;
import smartPark.smart_park.models.entity.Immobilisation;
import smartPark.smart_park.models.entity.Transaction;
import smartPark.smart_park.models.entity.Utilisateur;
import smartPark.smart_park.models.entity.enums.EtatTransaction;
import smartPark.smart_park.models.entity.enums.Role;
import smartPark.smart_park.models.entity.enums.TypeTransaction;
import smartPark.smart_park.repository.AgenceRepository;
import smartPark.smart_park.repository.ImmobilisationRepository;
import smartPark.smart_park.repository.TransactionRepository;
import smartPark.smart_park.repository.UtilisateurRepository;
import smartPark.smart_park.services.TransactionService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private final TransactionRepository transactionRepository;
    @Autowired
    private final ImmobilisationRepository immobilisationRepository;
    @Autowired
    private final AgenceRepository agenceRepository;
    @Autowired
    private final UtilisateurRepository utilisateurRepository;
    @Autowired
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionResponseDto creerTransaction(TransactionRequestDto requestDto) {
        log.info("Création d'une nouvelle transaction pour l'immobilisation ID: {}", requestDto.getImmobilisationId());

        // Vérifier que l'immobilisation existe
        Immobilisation immobilisation = immobilisationRepository.findById(requestDto.getImmobilisationId())
                .orElseThrow(() -> new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + requestDto.getImmobilisationId()));

        // Vérifier que le demandeur existe
        Utilisateur demandeur = utilisateurRepository.findById(requestDto.getDemandeurId())
                .orElseThrow(() -> new ResourceNotFoundException("Demandeur non trouvé avec l'ID: " + requestDto.getDemandeurId()));

        // Vérifier qu'il n'y a pas déjà une transaction en attente pour cette immobilisation
        if (transactionRepository.hasTransactionsEnAttenteForImmobilisation(requestDto.getImmobilisationId())) {
            throw new BusinessException("Une transaction est déjà en attente pour cette immobilisation");
        }

        Transaction transaction = transactionMapper.toEntity(requestDto);
        transaction.setImmobilisation(immobilisation);
        transaction.setDemandeur(demandeur);

        // Gestion des agences selon le type de transaction
        validerAgencesSelonType(transaction, requestDto);

        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Transaction créée avec succès. ID: {}", savedTransaction.getId());

        return transactionMapper.toResponseDto(savedTransaction);
    }

    private void validerAgencesSelonType(Transaction transaction, TransactionRequestDto requestDto) {
        TypeTransaction type = requestDto.getTypeTransaction();

        switch (type) {
            case TRANSFERT:
                // Pour un transfert, agence source et destination sont obligatoires
                if (requestDto.getAgenceSourceId() == null || requestDto.getAgenceDestinationId() == null) {
                    throw new ValidationException("Pour un transfert, les agences source et destination sont obligatoires");
                }
                if (requestDto.getAgenceSourceId().equals(requestDto.getAgenceDestinationId())) {
                    throw new ValidationException("Les agences source et destination doivent être différentes");
                }

                Agence agenceSource = agenceRepository.findById(requestDto.getAgenceSourceId())
                        .orElseThrow(() -> new ResourceNotFoundException("Agence source non trouvée avec l'ID: " + requestDto.getAgenceSourceId()));
                Agence agenceDestination = agenceRepository.findById(requestDto.getAgenceDestinationId())
                        .orElseThrow(() -> new ResourceNotFoundException("Agence destination non trouvée avec l'ID: " + requestDto.getAgenceDestinationId()));

                transaction.setAgenceSource(agenceSource);
                transaction.setAgenceDestination(agenceDestination);
                break;

            case AFFECTATION:
                // Pour une affectation, seule l'agence destination est obligatoire
                if (requestDto.getAgenceDestinationId() == null) {
                    throw new ValidationException("Pour une affectation, l'agence destination est obligatoire");
                }

                Agence agenceDest = agenceRepository.findById(requestDto.getAgenceDestinationId())
                        .orElseThrow(() -> new ResourceNotFoundException("Agence destination non trouvée avec l'ID: " + requestDto.getAgenceDestinationId()));

                transaction.setAgenceDestination(agenceDest);
                // L'agence source peut être null (immobilisation non encore affectée)
                if (requestDto.getAgenceSourceId() != null) {
                    Agence agenceSrc = agenceRepository.findById(requestDto.getAgenceSourceId())
                            .orElseThrow(() -> new ResourceNotFoundException("Agence source non trouvée avec l'ID: " + requestDto.getAgenceSourceId()));
                    transaction.setAgenceSource(agenceSrc);
                }
                break;

            case DESAFFECTATION:
                // Pour une désaffectation, seule l'agence source est nécessaire
                if (requestDto.getAgenceSourceId() == null) {
                    throw new ValidationException("Pour une désaffectation, l'agence source est obligatoire");
                }

                Agence agenceSrc2 = agenceRepository.findById(requestDto.getAgenceSourceId())
                        .orElseThrow(() -> new ResourceNotFoundException("Agence source non trouvée avec l'ID: " + requestDto.getAgenceSourceId()));

                transaction.setAgenceSource(agenceSrc2);
                // L'agence destination reste null pour une désaffectation
                break;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionResponseDto obtenirTransactionParId(Long id) {
        log.info("Récupération de la transaction ID: {}", id);

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction non trouvée avec l'ID: " + id));

        return transactionMapper.toResponseDto(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponseDto> obtenirToutesLesTransactions(Pageable pageable) {
        log.info("Récupération de toutes les transactions avec pagination");

        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        return transactions.map(transactionMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> obtenirToutesLesTransactions() {
        log.info("Récupération de toutes les transactions");

        List<Transaction> transactions = transactionRepository.findAll();
        return transactionMapper.toResponseDtoList(transactions);
    }

    @Override
    public TransactionResponseDto mettreAJourTransaction(Long id, TransactionUpdateDto updateDto) {
        log.info("Mise à jour de la transaction ID: {}", id);

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction non trouvée avec l'ID: " + id));

        // Vérifier que la transaction est en attente
        if (!transaction.peutEtreValidee()) {
            throw new BusinessException("Seules les transactions en attente peuvent être modifiées");
        }

        // Vérifier les nouvelles relations si elles sont modifiées
        if (updateDto.getImmobilisationId() != null && !updateDto.getImmobilisationId().equals(transaction.getImmobilisation().getId())) {
            Immobilisation immobilisation = immobilisationRepository.findById(updateDto.getImmobilisationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + updateDto.getImmobilisationId()));
            transaction.setImmobilisation(immobilisation);
        }

        if (updateDto.getDemandeurId() != null && !updateDto.getDemandeurId().equals(transaction.getDemandeur().getId())) {
            Utilisateur demandeur = utilisateurRepository.findById(updateDto.getDemandeurId())
                    .orElseThrow(() -> new ResourceNotFoundException("Demandeur non trouvé avec l'ID: " + updateDto.getDemandeurId()));
            transaction.setDemandeur(demandeur);
        }

        // Gestion des agences
        if (updateDto.getAgenceSourceId() != null) {
            Agence agenceSource = agenceRepository.findById(updateDto.getAgenceSourceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence source non trouvée avec l'ID: " + updateDto.getAgenceSourceId()));
            transaction.setAgenceSource(agenceSource);
        }

        if (updateDto.getAgenceDestinationId() != null) {
            Agence agenceDestination = agenceRepository.findById(updateDto.getAgenceDestinationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence destination non trouvée avec l'ID: " + updateDto.getAgenceDestinationId()));
            transaction.setAgenceDestination(agenceDestination);
        }

        transactionMapper.updateEntity(transaction, updateDto);
        Transaction updatedTransaction = transactionRepository.save(transaction);

        log.info("Transaction mise à jour avec succès. ID: {}", updatedTransaction.getId());
        return transactionMapper.toResponseDto(updatedTransaction);
    }

    @Override
    public void supprimerTransaction(Long id) {
        log.info("Suppression de la transaction ID: {}", id);

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction non trouvée avec l'ID: " + id));

        // Vérifier que la transaction peut être supprimée (par exemple, seulement si en attente)
        if (!transaction.estEnAttente()) {
            throw new BusinessException("Seules les transactions en attente peuvent être supprimées");
        }

        transactionRepository.deleteById(id);
        log.info("Transaction supprimée avec succès. ID: {}", id);
    }

    @Override
    public TransactionResponseDto validerTransaction(Long id, TransactionValidationDto validationDto) {
        log.info("Validation de la transaction ID: {} - Décision: {}", id, validationDto.getValider() ? "VALIDER" : "REJETER");

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction non trouvée avec l'ID: " + id));

        // Vérifier que la transaction peut être validée
        if (!transaction.peutEtreValidee()) {
            throw new BusinessException("Cette transaction ne peut pas être validée (état actuel: " + transaction.getEtatTransaction() + ")");
        }

        // Vérifier que le validateur existe et a les droits
        Utilisateur validateur = utilisateurRepository.findById(validationDto.getValidateurId())
                .orElseThrow(() -> new ResourceNotFoundException("Validateur non trouvé avec l'ID: " + validationDto.getValidateurId()));

        if (!validateur.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException("Seuls les administrateurs peuvent valider des transactions");
        }

        // Appliquer la décision
        if (validationDto.getValider()) {
            transaction.setEtatTransaction(EtatTransaction.VALIDEE);
            transaction.setDateValidation(LocalDateTime.now());

            // Mettre à jour l'agence de l'immobilisation selon le type de transaction
            mettreAJourImmobilisationApreValidation(transaction);
        } else {
            transaction.setEtatTransaction(EtatTransaction.REJETEE);
            transaction.setMotifRejet(validationDto.getMotifRejet());
        }

        transaction.setValidateur(validateur);
        if (validationDto.getObservations() != null) {
            transaction.setObservations(validationDto.getObservations());
        }

        Transaction savedTransaction = transactionRepository.save(transaction);

        log.info("Transaction {} avec succès. ID: {}", validationDto.getValider() ? "validée" : "rejetée", savedTransaction.getId());
        return transactionMapper.toResponseDto(savedTransaction);
    }

    private void mettreAJourImmobilisationApreValidation(Transaction transaction) {
        Immobilisation immobilisation = transaction.getImmobilisation();

        switch (transaction.getTypeTransaction()) {
            case TRANSFERT:
            case AFFECTATION:
                // Affecter l'immobilisation à l'agence destination
                immobilisation.setAgence(transaction.getAgenceDestination());
                break;

            case DESAFFECTATION:
                // Désaffecter l'immobilisation (agence = null)
                immobilisation.setAgence(null);
                break;
        }

        immobilisationRepository.save(immobilisation);
        log.info("Immobilisation ID: {} mise à jour après validation de la transaction", immobilisation.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> obtenirTransactionsParImmobilisation(Long immobilisationId) {
        log.info("Récupération des transactions pour l'immobilisation ID: {}", immobilisationId);

        if (!immobilisationRepository.existsById(immobilisationId)) {
            throw new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + immobilisationId);
        }

        List<Transaction> transactions = transactionRepository.findByImmobilisationId(immobilisationId);
        return transactionMapper.toResponseDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponseDto> obtenirTransactionsParImmobilisation(Long immobilisationId, Pageable pageable) {
        log.info("Récupération des transactions pour l'immobilisation ID: {} avec pagination", immobilisationId);

        if (!immobilisationRepository.existsById(immobilisationId)) {
            throw new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + immobilisationId);
        }

        Page<Transaction> transactions = transactionRepository.findByImmobilisationId(immobilisationId, pageable);
        return transactions.map(transactionMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> obtenirTransactionsParDemandeur(Long demandeurId) {
        log.info("Récupération des transactions pour le demandeur ID: {}", demandeurId);

        if (!utilisateurRepository.existsById(demandeurId)) {
            throw new ResourceNotFoundException("Demandeur non trouvé avec l'ID: " + demandeurId);
        }

        List<Transaction> transactions = transactionRepository.findByDemandeurId(demandeurId);
        return transactionMapper.toResponseDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponseDto> obtenirTransactionsParDemandeur(Long demandeurId, Pageable pageable) {
        log.info("Récupération des transactions pour le demandeur ID: {} avec pagination", demandeurId);

        if (!utilisateurRepository.existsById(demandeurId)) {
            throw new ResourceNotFoundException("Demandeur non trouvé avec l'ID: " + demandeurId);
        }

        Page<Transaction> transactions = transactionRepository.findByDemandeurId(demandeurId, pageable);
        return transactions.map(transactionMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> obtenirTransactionsParValidateur(Long validateurId) {
        log.info("Récupération des transactions pour le validateur ID: {}", validateurId);

        if (!utilisateurRepository.existsById(validateurId)) {
            throw new ResourceNotFoundException("Validateur non trouvé avec l'ID: " + validateurId);
        }

        List<Transaction> transactions = transactionRepository.findByValidateurId(validateurId);
        return transactionMapper.toResponseDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponseDto> obtenirTransactionsParValidateur(Long validateurId, Pageable pageable) {
        log.info("Récupération des transactions pour le validateur ID: {} avec pagination", validateurId);

        if (!utilisateurRepository.existsById(validateurId)) {
            throw new ResourceNotFoundException("Validateur non trouvé avec l'ID: " + validateurId);
        }

        Page<Transaction> transactions = transactionRepository.findByValidateurId(validateurId, pageable);
        return transactions.map(transactionMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> obtenirTransactionsParEtat(EtatTransaction etatTransaction) {
        log.info("Récupération des transactions par état: {}", etatTransaction);

        List<Transaction> transactions = transactionRepository.findByEtatTransaction(etatTransaction);
        return transactionMapper.toResponseDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponseDto> obtenirTransactionsParEtat(EtatTransaction etatTransaction, Pageable pageable) {
        log.info("Récupération des transactions par état: {} avec pagination", etatTransaction);

        Page<Transaction> transactions = transactionRepository.findByEtatTransaction(etatTransaction, pageable);
        return transactions.map(transactionMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> obtenirTransactionsParType(TypeTransaction typeTransaction) {
        log.info("Récupération des transactions par type: {}", typeTransaction);

        List<Transaction> transactions = transactionRepository.findByTypeTransaction(typeTransaction);
        return transactionMapper.toResponseDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponseDto> obtenirTransactionsParType(TypeTransaction typeTransaction, Pageable pageable) {
        log.info("Récupération des transactions par type: {} avec pagination", typeTransaction);

        Page<Transaction> transactions = transactionRepository.findByTypeTransaction(typeTransaction, pageable);
        return transactions.map(transactionMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> obtenirTransactionsParAgenceSource(Long agenceSourceId) {
        log.info("Récupération des transactions pour l'agence source ID: {}", agenceSourceId);

        List<Transaction> transactions = transactionRepository.findByAgenceSourceId(agenceSourceId);
        return transactionMapper.toResponseDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponseDto> obtenirTransactionsParAgenceSource(Long agenceSourceId, Pageable pageable) {
        log.info("Récupération des transactions pour l'agence source ID: {} avec pagination", agenceSourceId);

        Page<Transaction> transactions = transactionRepository.findByAgenceSourceId(agenceSourceId, pageable);
        return transactions.map(transactionMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> obtenirTransactionsParAgenceDestination(Long agenceDestinationId) {
        log.info("Récupération des transactions pour l'agence destination ID: {}", agenceDestinationId);

        List<Transaction> transactions = transactionRepository.findByAgenceDestinationId(agenceDestinationId);
        return transactionMapper.toResponseDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponseDto> obtenirTransactionsParAgenceDestination(Long agenceDestinationId, Pageable pageable) {
        log.info("Récupération des transactions pour l'agence destination ID: {} avec pagination", agenceDestinationId);

        Page<Transaction> transactions = transactionRepository.findByAgenceDestinationId(agenceDestinationId, pageable);
        return transactions.map(transactionMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> obtenirTransactionsParAgenceImpliquee(Long agenceId) {
        log.info("Récupération des transactions impliquant l'agence ID: {}", agenceId);

        List<Transaction> transactions = transactionRepository.findByAgenceImpliquee(agenceId);
        return transactionMapper.toResponseDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponseDto> obtenirTransactionsParAgenceImpliquee(Long agenceId, Pageable pageable) {
        log.info("Récupération des transactions impliquant l'agence ID: {} avec pagination", agenceId);

        Page<Transaction> transactions = transactionRepository.findByAgenceImpliquee(agenceId, pageable);
        return transactions.map(transactionMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> obtenirTransactionsParPeriodeDemande(LocalDateTime dateDebut, LocalDateTime dateFin) {
        log.info("Récupération des transactions entre {} et {}", dateDebut, dateFin);

        if (dateDebut.isAfter(dateFin)) {
            throw new ValidationException("La date de début doit être antérieure à la date de fin");
        }

        List<Transaction> transactions = transactionRepository.findByDateDemandeBetween(dateDebut, dateFin);
        return transactionMapper.toResponseDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> obtenirTransactionsParPeriodeValidation(LocalDateTime dateDebut, LocalDateTime dateFin) {
        log.info("Récupération des transactions validées entre {} et {}", dateDebut, dateFin);

        if (dateDebut.isAfter(dateFin)) {
            throw new ValidationException("La date de début doit être antérieure à la date de fin");
        }

        List<Transaction> transactions = transactionRepository.findByDateValidationBetween(dateDebut, dateFin);
        return transactionMapper.toResponseDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> obtenirTransactionsEnAttente() {
        log.info("Récupération des transactions en attente");

        List<Transaction> transactions = transactionRepository.findTransactionsEnAttente();
        return transactionMapper.toResponseDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponseDto> obtenirTransactionsEnAttente(Pageable pageable) {
        log.info("Récupération des transactions en attente avec pagination");

        Page<Transaction> transactions = transactionRepository.findTransactionsEnAttente(pageable);
        return transactions.map(transactionMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponseDto> rechercherAvecCriteres(
            Long immobilisationId,
            Long demandeurId,
            Long validateurId,
            EtatTransaction etatTransaction,
            TypeTransaction typeTransaction,
            Long agenceSourceId,
            Long agenceDestinationId,
            LocalDateTime dateDebut,
            LocalDateTime dateFin,
            Pageable pageable) {

        log.info("Recherche de transactions avec critères multiples");

        if (dateDebut != null && dateFin != null && dateDebut.isAfter(dateFin)) {
            throw new ValidationException("La date de début doit être antérieure à la date de fin");
        }

        Page<Transaction> transactions = transactionRepository.findWithCriteria(
                immobilisationId, demandeurId, validateurId, etatTransaction, typeTransaction,
                agenceSourceId, agenceDestinationId, dateDebut, dateFin, pageable
        );

        return transactions.map(transactionMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> obtenirTransactionsRecentes(Long immobilisationId, int limite) {
        log.info("Récupération des {} transactions les plus récentes pour l'immobilisation ID: {}", limite, immobilisationId);

        if (!immobilisationRepository.existsById(immobilisationId)) {
            throw new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + immobilisationId);
        }

        List<Transaction> transactions = transactionRepository.findRecentTransactionsByImmobilisation(immobilisationId);

        // Limiter le nombre de résultats
        if (transactions.size() > limite) {
            transactions = transactions.subList(0, limite);
        }

        return transactionMapper.toResponseDtoList(transactions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> obtenirStatistiquesParEtat() {
        log.info("Récupération des statistiques par état de transaction");

        return transactionRepository.countTransactionsByEtat();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> obtenirStatistiquesParType() {
        log.info("Récupération des statistiques par type de transaction");

        return transactionRepository.countTransactionsByType();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean transactionExiste(Long id) {
        return transactionRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasTransactionsEnAttenteForImmobilisation(Long immobilisationId) {
        return transactionRepository.hasTransactionsEnAttenteForImmobilisation(immobilisationId);
    }

        @Override
        public TransactionResponseDto annulerTransaction(Long id, Long utilisateurId) {
            log.info("Annulation de la transaction ID: {} par l'utilisateur ID: {}", id, utilisateurId);

            Transaction transaction = transactionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Transaction non trouvée avec l'ID: " + id));

            // Vérifier que la transaction peut être annulée
            if (!transaction.estEnAttente()) {
                throw new BusinessException("Seules les transactions en attente peuvent être annulées");
            }

            // Vérifier les droits (seul le demandeur ou un admin peut annuler)
            Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                    .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + utilisateurId));

            if (!utilisateur.getRole().equals(Role.ADMIN) && !transaction.getDemandeur().getId().equals(utilisateurId)) {
                throw new ForbiddenException("Seul le demandeur ou un administrateur peut annuler cette transaction");
            }

            // Marquer comme rejetée avec un motif d'annulation
            transaction.setEtatTransaction(EtatTransaction.REJETEE);
            transaction.setMotifRejet("Transaction annulée par " +
                    (utilisateur.getRole().equals(Role.ADMIN) ? "l'administrateur" : "le demandeur"));
            transaction.setValidateur(utilisateur);
            transaction.setDateValidation(LocalDateTime.now());

            Transaction savedTransaction = transactionRepository.save(transaction);

            log.info("Transaction annulée avec succès. ID: {}", savedTransaction.getId());
            return transactionMapper.toResponseDto(savedTransaction);
        }
    }