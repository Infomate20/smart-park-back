package smartPark.smart_park.assistance.iaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import  org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.Map;

@Service
public class GroqService {

    @Autowired
    @Value("${groq.api.url}")
    private String groqApiUrl;

    private static final Logger log = LoggerFactory.getLogger(AiQueryService.class);

    @Autowired
    @Value("${groq.api.key}")
    private String groqApiKey;

    @Autowired
    @Value("${groq.model}")
    private String groqModel;

    private final RestTemplate restTemplate= new RestTemplate();
    /**
     * Envoie le prompt à l'API de Groq et retourne la réponse textuelle.
     * @param prompt Le prompt complet incluant le schéma et la question.
     * @return La requête SQL générée par l'IA, ou un message d'erreur.
     */

    public String generateSqlFromQuestion(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(groqApiKey);

        // Structure du corps de la requête pour l'API Groq (similaire à OpenAI)
        Map<String, Object> message = Map.of("role", "user", "content", prompt);
        Map<String, Object> requestBody = Map.of(
                "messages", List.of(message),
                "model", groqModel
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            log.debug("Envoi de la requête à l'API Groq : {}", groqApiUrl);
            ResponseEntity<Map> response = restTemplate.postForEntity(groqApiUrl, entity, Map.class);

            // Extraction de la réponse de l'objet JSON complexe retourné par Groq
            if (response.getBody() != null && response.getBody().get("choices") instanceof List) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> firstChoice = choices.get(0);
                    if (firstChoice.get("message") instanceof Map) {
                        Map<String, String> messageMap = (Map<String, String>) firstChoice.get("message");
                        return messageMap.get("content").trim();
                    }
                }
            }
            return "ERROR: Invalid response structure from AI service.";
        } catch (HttpClientErrorException e) {
            log.error("Erreur client lors de l'appel à l'API Groq. Statut: {}, Réponse: {}", e.getStatusCode(), e.getResponseBodyAsString());
            return "ERROR: Client error while communicating with AI service.";
        } catch (RestClientException e) {
            // ERREUR GÉNÉRALE DE COMMUNICATION (réseau, DNS, etc.)
            log.error("Échec de la communication avec l'API Groq.", e);
            return "ERROR: Failed to communicate with AI service.";
        }
    }
    }
