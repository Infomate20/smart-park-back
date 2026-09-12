package smartPark.smart_park.assistance.iaService;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smartPark.smart_park.assistance.AiTrainingLog;
import smartPark.smart_park.exceptions.BusinessException;
import smartPark.smart_park.assistance.PromptFactory;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiQueryService {

    @Autowired
    private static final Logger trainingLogger = LoggerFactory.getLogger("AiTrainingLogger");
    @Autowired
    private final ObjectMapper objectMapper;
    @Autowired
    private final PromptFactory promptFactory;
    @Autowired
    private final GroqService groqService;
    @Autowired
    private final SqlValidatorService sqlValidatorService;
    @Autowired
    private final NativeQueryService nativeQueryService;

    public List<Map<String, Object>> processQuestion(String userQuestion) {
        log.info("Nouvelle question IA de l'utilisateur : '{}'", userQuestion);


            String prompt = promptFactory.createSqlGenerationPrompt(userQuestion);
            String generatedSql = groqService.generateSqlFromQuestion(prompt);
            log.info("SQL généré par l'IA : '{}'", generatedSql);

            if (generatedSql.startsWith("ERROR:")) {
                // Le modèle signale que la question ne peut pas être traitée
                // avec le schéma disponible : c'est un refus métier, pas une panne.
                throw new BusinessException(generatedSql);
            }

            sqlValidatorService.validate(generatedSql);
            log.info("Validation du SQL réussie.");

            try {
                AiTrainingLog trainingEntry = new AiTrainingLog(userQuestion, generatedSql, Instant.now().toString());
            } catch (Exception e) {
                log.error("Echec de l'ecriture dans le log d'entrainement IA", e);
            }
            List<Map<String, Object>> result = nativeQueryService.executeSelectQuery(generatedSql);
            log.info("Exécution de la requête réussie, {} lignes retournées.", result.size());

            return result;
        }

}