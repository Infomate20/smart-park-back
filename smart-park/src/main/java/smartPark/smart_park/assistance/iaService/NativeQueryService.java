package smartPark.smart_park.assistance.iaService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class NativeQueryService {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Exécute une requête SELECT native et retourne le résultat sous forme de liste de maps.
     * @param sql La requête SQL validée.
     * @return Une liste de lignes, où chaque ligne est une map (nom_colonne -> valeur).
     */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> executeSelectQuery(String sql) {
        Query query = entityManager.createNativeQuery(sql);
        // Utilise un ResultTransformer pour convertir le résultat en Map
        query.unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> result = query.getResultList();
        return result;
    }
}