package land.leets.domain.interview.presentation.dto.req;

import jakarta.validation.constraints.NotNull;
import land.leets.domain.interview.domain.Interview;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class FixedInterviewRequest {
    @NotNull
    private LocalDateTime fixedInterviewDate;

    @NotNull
    private String place;

    public void updateInterview(Interview interview) {
        interview.setFixedInterviewDate(this.fixedInterviewDate);
        interview.setPlace(this.place);
    }
}
