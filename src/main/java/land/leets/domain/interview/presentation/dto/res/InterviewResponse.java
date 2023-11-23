package land.leets.domain.interview.presentation.dto.res;

import land.leets.domain.interview.type.HasInterview;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class InterviewResponse {
    private HasInterview hasInterview;
    private LocalDateTime fixedInterviewDate;
}
