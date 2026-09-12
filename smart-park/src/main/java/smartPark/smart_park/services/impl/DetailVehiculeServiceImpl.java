package smartPark.smart_park.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartPark.smart_park.exceptions.BusinessException;
import smartPark.smart_park.exceptions.ResourceNotFoundException;
import smartPark.smart_park.mapper.DetailVehiculeMapper;
import smartPark.smart_park.models.dto.request.DetailVehiculeRequestDto;
import smartPark.smart_park.models.dto.response.DetailVehiculeResponseDto;
import smartPark.smart_park.models.entity.DetailVehicule;
import smartPark.smart_park.models.entity.Immobilisation;
import smartPark.smart_park.repository.DetailVehiculeRepository;
import smartPark.smart_park.repository.ImmobilisationRepository;
import smartPark.smart_park.services.DetailVehiculeService;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DetailVehiculeServiceImpl implements DetailVehiculeService {

    private final DetailVehiculeRepository detailVehiculeRepository;
    private final ImmobilisationRepository immobilisationRepository;
    private final DetailVehiculeMapper detailVehiculeMapper;

    @Override
    public DetailVehiculeResponseDto enregistrerDetailVehicule(Long immobilisationId,
                                                               DetailVehiculeRequestDto requestDto) {
        log.info("Enregistrement des informations véhicule de l'immobilisation ID: {}", immobilisationId);

        Immobilisation immobilisation = immobilisationRepository.findById(immobilisationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Immobilisation non trouvée avec l'ID: " + immobilisationId));

        String immatriculation = detailVehiculeMapper.normaliserImmatriculation(requestDto.getImmatriculation());

        DetailVehicule detail = detailVehiculeRepository.findByImmobilisationId(immobilisationId).orElse(null);

        // Une immatriculation ne peut désigner qu'un seul véhicule du parc
        Long idCourant = detail != null ? detail.getId() : -1L;
        if (detailVehiculeRepository.existsByImmatriculationAndIdNot(immatriculation, idCourant)) {
            throw new BusinessException("Un autre véhicule est déjà enregistré avec l'immatriculation " + immatriculation);
        }

        if (detail == null) {
            detail = detailVehiculeMapper.toEntity(requestDto, immobilisation);
        } else {
            detailVehiculeMapper.updateEntityFromDto(requestDto, detail);
        }

        DetailVehicule enregistre = detailVehiculeRepository.save(detail);

        log.info("Informations véhicule enregistrées pour l'immobilisation {} (immatriculation {})",
                immobilisationId, enregistre.getImmatriculation());
        return detailVehiculeMapper.toResponseDto(enregistre);
    }

    @Override
    @Transactional(readOnly = true)
    public DetailVehiculeResponseDto obtenirDetailVehicule(Long immobilisationId) {
        log.info("Récupération des informations véhicule de l'immobilisation ID: {}", immobilisationId);

        DetailVehicule detail = detailVehiculeRepository.findByImmobilisationId(immobilisationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aucune information véhicule pour l'immobilisation ID: " + immobilisationId));

        return detailVehiculeMapper.toResponseDto(detail);
    }

    @Override
    @Transactional(readOnly = true)
    public DetailVehiculeResponseDto obtenirParImmatriculation(String immatriculation) {
        String recherchee = detailVehiculeMapper.normaliserImmatriculation(immatriculation);
        log.info("Recherche du véhicule immatriculé: {}", recherchee);

        DetailVehicule detail = detailVehiculeRepository.findByImmatriculation(recherchee)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aucun véhicule trouvé avec l'immatriculation: " + recherchee));

        return detailVehiculeMapper.toResponseDto(detail);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DetailVehiculeResponseDto> obtenirTousLesVehicules(Pageable pageable) {
        log.info("Récupération des véhicules du parc (page {}, taille {})",
                pageable.getPageNumber(), pageable.getPageSize());
        return detailVehiculeRepository.findAll(pageable).map(detailVehiculeMapper::toResponseDto);
    }

    @Override
    public void supprimerDetailVehicule(Long immobilisationId) {
        log.info("Suppression des informations véhicule de l'immobilisation ID: {}", immobilisationId);

        DetailVehicule detail = detailVehiculeRepository.findByImmobilisationId(immobilisationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aucune information véhicule pour l'immobilisation ID: " + immobilisationId));

        detailVehiculeRepository.delete(detail);
        log.info("Informations véhicule supprimées pour l'immobilisation ID: {}", immobilisationId);
    }
}
