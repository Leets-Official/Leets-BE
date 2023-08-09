package land.leets.domain.application.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationRequest {
    private float gpa;
    private String algorithm;
    private String portfolio;
    private String position;
    private String interview;
    private String enhancement;
    private String level;
    private String pros;
    private String goal;
    private String completion;
}
