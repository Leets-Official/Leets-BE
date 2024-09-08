package land.leets.domain.interview.usecase;

import land.leets.domain.interview.domain.Interview;
import land.leets.domain.interview.presentation.dto.req.FixedInterviewRequest;
import land.leets.domain.interview.presentation.dto.req.InterviewAttendanceRequest;

public interface UpdateInterview {
    Interview byUser(InterviewAttendanceRequest request);

    Interview byAdmin(Long id, FixedInterviewRequest request);
}
