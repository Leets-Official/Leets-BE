package land.leets.domain.interview.presentation.dto.res;

import land.leets.domain.interview.type.HasInterview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class InterviewDetailsResponse {
    private Long id;
    private HasInterview hasInterview;
    private LocalDateTime fixedInterviewDate;
    private String place;
}
