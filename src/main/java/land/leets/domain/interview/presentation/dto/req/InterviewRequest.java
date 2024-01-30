package land.leets.domain.interview.presentation.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class InterviewRequest {
    @NotNull
    private Long applicationId;

    @NotNull
    private LocalDateTime fixedInterviewDate;

    @NotNull
    private String place;
}
