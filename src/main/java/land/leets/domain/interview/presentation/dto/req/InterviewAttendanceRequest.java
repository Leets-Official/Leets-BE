package land.leets.domain.interview.presentation.dto.req;

import jakarta.validation.constraints.NotNull;
import land.leets.domain.interview.type.HasInterview;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InterviewAttendanceRequest {
    @NotNull
    private String email;

    @NotNull
    private HasInterview hasInterview;
}
