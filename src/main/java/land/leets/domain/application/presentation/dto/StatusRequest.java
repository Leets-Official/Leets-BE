package land.leets.domain.application.presentation.dto;

import land.leets.domain.application.type.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StatusRequest {

    private ApplicationStatus applicationStatus;
    private LocalDateTime fixedInterviewDate;
    private String place;
}
