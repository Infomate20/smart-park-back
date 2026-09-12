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
import smartPark.smart_park.mapper.CategorieMapper;
import smartPark.smart_park.models.dto.request.CategorieRequestDto;
import smartPark.smart_park.models.dto.response.CategorieResponseDto;
import smartPark.smart_park.models.entity.Categorie;
import smartPark.smart_park.repository.CategorieRepository;
import smartPark.smart_park.services.CategorieService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategorieServiceImpl implements CategorieService {

    @Autowired
    private final CategorieRepository categorieRepository;
    @Autowired
    private final CategorieMapper categorieMapper;

    @Override
    public CategorieResponseDto creerCategorie(CategorieRequestDto requestDto) {
        log.info("Création d'une nouvelle catégorie avec le nom: {}", requestDto.getNom());

        // Vérifier l'unicité du nom
        if (categorieRepository.findByNom(requestDto.getNom()).isPresent()) {
            throw new BusinessException("Une catégorie avec ce nom existe déjà");
        }

        // Vérifier l'unicité du code (comparé sous sa forme normalisée)
        String codeNormalise = categorieMapper.normaliserCode(requestDto.getCode());
        if (categorieRepository.findByCode(codeNormalise).isPresent()) {
            throw new BusinessException("Une catégorie avec le code " + codeNormalise + " existe déjà");
        }

        Categorie categorie = categorieMapper.toEntity(requestDto);
        Categorie categorieEnregistree = categorieRepository.save(categorie);

        log.info("Catégorie créée avec succès avec l'ID: {}", categorieEnregistree.getId());
        return categorieMapper.toResponseDto(categorieEnregistree);
    }

    @Override
    @Transactional(readOnly = true)
    public CategorieResponseDto obtenirCategorieParId(Long id) {
        log.info("Recherche de la catégorie avec l'ID: {}", id);

        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée avec l'ID: " + id));

        return categorieMapper.toResponseDto(categorie);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategorieResponseDto> obtenirToutesLesCategories() {
        log.info("Récupération de toutes les catégories");

        List<Categorie> categories = categorieRepository.findAll();
        return categorieMapper.toResponseDtoList(categories);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategorieResponseDto> obtenirCategoriesActives() {
        log.info("Récupération des catégories actives");

        List<Categorie> categories = categorieRepository.findByActifTrue();
        return categorieMapper.toResponseDtoList(categories);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategorieResponseDto> obtenirCategoriesAvecPagination(String searchTerm,Boolean actif,Pageable pageable) {
        log.info("Récupération des catégories avec pagination: term{},etat{},page {}, taille {}",
                searchTerm, actif,pageable.getPageNumber(), pageable.getPageSize());
        String Search = (searchTerm != null && !searchTerm.trim().isEmpty()) ? searchTerm : null;

        Page<Categorie> categories = categorieRepository.findWithFilter(Search, actif,pageable);
        return categories.map(categorieMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategorieResponseDto> rechercherCategories(String searchTerm, Pageable pageable) {
        log.info("Recherche de catégories avec le terme: {}", searchTerm);

        Page<Categorie> categories = categorieRepository.findBySearchTerm(searchTerm, pageable);
        return categories.map(categorieMapper::toResponseDto);
    }

    @Override
    public CategorieResponseDto modifierCategorie(Long id, CategorieRequestDto requestDto) {
        log.info("Modification de la catégorie avec l'ID: {}", id);

        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée avec l'ID: " + id));

        // Vérifier l'unicité du nom (exclure la catégorie actuelle)
        if (categorieRepository.existsByNomAndIdNot(requestDto.getNom(), id)) {
            throw new BusinessException("Une autre catégorie avec ce nom existe déjà");
        }

        // Idem pour le code, uniquement s'il est fourni (mise à jour partielle)
        if (requestDto.getCode() != null) {
            String codeNormalise = categorieMapper.normaliserCode(requestDto.getCode());
            if (categorieRepository.existsByCodeAndIdNot(codeNormalise, id)) {
                throw new BusinessException("Une autre catégorie avec le code " + codeNormalise + " existe déjà");
            }
        }

        categorieMapper.updateEntityFromDto(requestDto, categorie);
        Categorie categorieModifiee = categorieRepository.save(categorie);

        log.info("Catégorie modifiée avec succès avec l'ID: {}", id);
        return categorieMapper.toResponseDto(categorieModifiee);
    }

    @Override
    public void supprimerCategorie(Long id) {
        log.info("Suppression de la catégorie avec l'ID: {}", id);

        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée avec l'ID: " + id));

        // Vérifier s'il y a des immobilisations associées
        if (categorie.getImmobilisations() != null && !categorie.getImmobilisations().isEmpty()) {
            throw new BusinessException("Impossible de supprimer cette catégorie car elle contient des immobilisations");
        }

        categorieRepository.delete(categorie);
        log.info("Catégorie supprimée avec succès avec l'ID: {}", id);
    }

    @Override
    public void desactiverCategorie(Long id) {
        log.info("Désactivation de la catégorie avec l'ID: {}", id);

        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée avec l'ID: " + id));

        categorie.setActif(false);
        categorieRepository.save(categorie);

        log.info("Catégorie désactivée avec succès avec l'ID: {}", id);
    }

    @Override
    public void activerCategorie(Long id) {
        log.info("Activation de la catégorie avec l'ID: {}", id);

        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée avec l'ID: " + id));

        categorie.setActif(true);
        categorieRepository.save(categorie);

        log.info("Catégorie activée avec succès avec l'ID: {}", id);
    }
}
