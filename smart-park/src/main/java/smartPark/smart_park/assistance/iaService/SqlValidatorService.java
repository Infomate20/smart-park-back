package smartPark.smart_park.assistance.iaService;

import smartPark.smart_park.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class SqlValidatorService {

    @Autowired
    private static final List<String> FORBIDDEN_KEYWORDS = List.of(
            "DELETE", "DROP", "INSERT", "UPDATE", "ALTER", "TRUNCATE", "CREATE",
            "RENAME", "GRANT", "REVOKE", "COMMIT", "ROLLBACK", "MERGE");

    /**
     * Valide une requête SQL générée pour s'assurer qu'elle est sûre.
     * @param sql La requête SQL à valider.
     * @throws IllegalArgumentException si la requête est jugée dangereuse.
     */
    @Autowired
    private static final Pattern SQL_COMMENT_PATTERN = Pattern.compile("(--|#|/\\*|\\*/)");

    /**
     * Valide une requête SQL générée pour s'assurer qu'elle est sûre.
     * @param sql La requête SQL à valider.
     * @throws IllegalArgumentException si la requête est jugée dangereuse.
     */
    public void validate(String sql) {
        if (sql == null || sql.isBlank()) {
            throw new ValidationException("La requête SQL générée est vide.");
        }

        String upperCaseSql = sql.trim().toUpperCase();

        // 1. Doit être une requête SELECT
        if (!upperCaseSql.startsWith("SELECT")) {
            throw new ValidationException("La requête doit commencer par SELECT.");
        }
        if (upperCaseSql.contains(";")) {
            throw new ValidationException("Requête invalide: les requêtes multiples (;) ne sont pas autorisées.");
        }
        if (SQL_COMMENT_PATTERN.matcher(sql).find()) {
            throw new ValidationException("Requête invalide: les commentaires SQL ne sont pas autorisés.");
        }
        for (String keyword : FORBIDDEN_KEYWORDS) {
            if (Pattern.compile("\\b" + keyword + "\\b").matcher(upperCaseSql).find()) {
                throw new ValidationException("Requête invalide: contient un mot-clé non autorisé : " + keyword);
            }
        }
    }


}