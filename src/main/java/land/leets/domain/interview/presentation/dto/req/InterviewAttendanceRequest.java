package land.leets.domain.interview.presentation.dto.req;

import jakarta.validation.constraints.NotNull;
import land.leets.domain.interview.domain.Interview;
import land.leets.domain.interview.type.HasInterview;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class InterviewAttendanceRequest {
    @NotNull
    private UUID uid;

    @NotNull
    private HasInterview hasInterview;

    public void updateInterview(Interview interview) {
        interview.setHasInterview(this.hasInterview);
    }
}
