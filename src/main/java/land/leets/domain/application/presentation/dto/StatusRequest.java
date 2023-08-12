package land.leets.domain.application.presentation.dto;

import land.leets.domain.application.type.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatusRequest {

    private ApplicationStatus applicationStatus;
    private String fixedInterviewDate;
}
