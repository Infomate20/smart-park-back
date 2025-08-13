package smartPark.smart_park.mapper;

import org.springframework.stereotype.Component;
import smartPark.smart_park.models.dto.request.TransactionRequestDto;
import smartPark.smart_park.models.dto.request.TransactionUpdateDto;
import smartPark.smart_park.models.dto.response.TransactionResponseDto;
import smartPark.smart_park.models.entity.Agence;
import smartPark.smart_park.models.entity.Immobilisation;
import smartPark.smart_park.models.entity.Transaction;
import smartPark.smart_park.models.entity.Utilisateur;
import smartPark.smart_park.models.entity.enums.EtatTransaction;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {

    public Transaction toEntity(TransactionRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Transaction transaction = new Transaction();
        transaction.setTypeTransaction(dto.getTypeTransaction());
        transaction.setEtatTransaction(EtatTransaction.EN_ATTENTE); // Par défaut
        transaction.setDateDemande(dto.getDateDemande());
        transaction.setMotif(dto.getMotif());
        transaction.setObservations(dto.getObservations());

        // Les relations seront définies dans le service
        if (dto.getImmobilisationId() != null) {
            Immobilisation immobilisation = new Immobilisation();
            immobilisation.setId(dto.getImmobilisationId());
            transaction.setImmobilisation(immobilisation);
        }

        if (dto.getAgenceSourceId() != null) {
            Agence agenceSource = new Agence();
            agenceSource.setId(dto.getAgenceSourceId());
            transaction.setAgenceSource(agenceSource);
        }

        if (dto.getAgenceDestinationId() != null) {
            Agence agenceDestination = new Agence();
            agenceDestination.setId(dto.getAgenceDestinationId());
            transaction.setAgenceDestination(agenceDestination);
        }

        if (dto.getDemandeurId() != null) {
            Utilisateur demandeur = new Utilisateur();
            demandeur.setId(dto.getDemandeurId());
            transaction.setDemandeur(demandeur);
        }

        return transaction;
    }

    public TransactionResponseDto toResponseDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        TransactionResponseDto dto = new TransactionResponseDto();
        dto.setId(transaction.getId());
        dto.setTypeTransaction(transaction.getTypeTransaction());
        dto.setEtatTransaction(transaction.getEtatTransaction());
        dto.setDateDemande(transaction.getDateDemande());
        dto.setDateValidation(transaction.getDateValidation());
        dto.setMotif(transaction.getMotif());
        dto.setObservations(transaction.getObservations());
        dto.setMotifRejet(transaction.getMotifRejet());
        dto.setCreatedAt(transaction.getCreatedAt());
        dto.setUpdatedAt(transaction.getUpdatedAt());

        // Informations de l'immobilisation
        if (transaction.getImmobilisation() != null) {
            dto.setImmobilisationId(transaction.getImmobilisation().getId());
            dto.setImmobilisationCode(transaction.getImmobilisation().getCodeImmobilisation());
            dto.setImmobilisationDesignation(transaction.getImmobilisation().getDesignation());
        }

        // Informations de l'agence source
        if (transaction.getAgenceSource() != null) {
            dto.setAgenceSourceId(transaction.getAgenceSource().getId());
            dto.setAgenceSourceNom(transaction.getAgenceSource().getNom());
            dto.setAgenceSourceCode(transaction.getAgenceSource().getCode());
        }

        // Informations de l'agence destination
        if (transaction.getAgenceDestination() != null) {
            dto.setAgenceDestinationId(transaction.getAgenceDestination().getId());
            dto.setAgenceDestinationNom(transaction.getAgenceDestination().getNom());
            dto.setAgenceDestinationCode(transaction.getAgenceDestination().getCode());
        }

        // Informations du demandeur
        if (transaction.getDemandeur() != null) {
            dto.setDemandeurId(transaction.getDemandeur().getId());
            dto.setDemandeurNom(transaction.getDemandeur().getNomUtilisateur());
            dto.setDemandeurPrenom(transaction.getDemandeur().getPrenom());
            dto.setDemandeurEmail(transaction.getDemandeur().getEmail());
        }

        // Informations du validateur
        if (transaction.getValidateur() != null) {
            dto.setValidateurId(transaction.getValidateur().getId());
            dto.setValidateurNom(transaction.getValidateur().getNomUtilisateur());
            dto.setValidateurPrenom(transaction.getValidateur().getPrenom());
            dto.setValidateurEmail(transaction.getValidateur().getEmail());
        }

        return dto;
    }

    public void updateEntity(Transaction transaction, TransactionUpdateDto dto) {
        if (dto == null || transaction == null) {
            return;
        }

        if (dto.getTypeTransaction() != null) {
            transaction.setTypeTransaction(dto.getTypeTransaction());
        }
        if (dto.getDateDemande() != null) {
            transaction.setDateDemande(dto.getDateDemande());
        }
        if (dto.getMotif() != null) {
            transaction.setMotif(dto.getMotif());
        }
        if (dto.getObservations() != null) {
            transaction.setObservations(dto.getObservations());
        }

        // Les relations seront mises à jour dans le service si nécessaire
        if (dto.getImmobilisationId() != null) {
            Immobilisation immobilisation = new Immobilisation();
            immobilisation.setId(dto.getImmobilisationId());
            transaction.setImmobilisation(immobilisation);
        }

        if (dto.getAgenceSourceId() != null) {
            Agence agenceSource = new Agence();
            agenceSource.setId(dto.getAgenceSourceId());
            transaction.setAgenceSource(agenceSource);
        }

        if (dto.getAgenceDestinationId() != null) {
            Agence agenceDestination = new Agence();
            agenceDestination.setId(dto.getAgenceDestinationId());
            transaction.setAgenceDestination(agenceDestination);
        }

        if (dto.getDemandeurId() != null) {
            Utilisateur demandeur = new Utilisateur();
            demandeur.setId(dto.getDemandeurId());
            transaction.setDemandeur(demandeur);
        }
    }

    public List<TransactionResponseDto> toResponseDtoList(List<Transaction> transactions) {
        if (transactions == null) {
            return null;
        }

        return transactions.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}