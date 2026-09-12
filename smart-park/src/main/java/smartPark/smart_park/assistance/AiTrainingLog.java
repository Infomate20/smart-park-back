package smartPark.smart_park.assistance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@AllArgsConstructor
public class AiTrainingLog {
    @Autowired
    private String userQuestion;
    @Autowired
    private String generatedSql;
    @Autowired
    private String timestamp;
}