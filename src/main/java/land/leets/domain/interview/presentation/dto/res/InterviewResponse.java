package land.leets.domain.interview.presentation.dto.res;

import land.leets.domain.interview.domain.Interview;
import land.leets.domain.interview.type.HasInterview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class InterviewResponse {
    private HasInterview hasInterview;
    private LocalDateTime fixedInterviewDate;

    public static InterviewResponse from(Interview interview) {
        if (interview == null) {
            return null;
        }
        return InterviewResponse.builder()
                .hasInterview(interview.getHasInterview())
                .fixedInterviewDate(interview.getFixedInterviewDate())
                .build();
    }
}
