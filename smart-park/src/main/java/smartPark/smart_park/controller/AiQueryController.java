package smartPark.smart_park.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smartPark.smart_park.assistance.iaService.AiQueryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiQueryController {

    private final AiQueryService aiQueryService;

    // DTO pour la requête
    @Getter
    @Setter
    static class AiQueryRequest {
        @NotBlank
        String question;
    }

    @PostMapping("/query")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Map<String, Object>>> handleQuery(@Valid @RequestBody AiQueryRequest request) {
        List<Map<String, Object>> result = aiQueryService.processQuestion(request.getQuestion());
        return ResponseEntity.ok(result);
    }
}