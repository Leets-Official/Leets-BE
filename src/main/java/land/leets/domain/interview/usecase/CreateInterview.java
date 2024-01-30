package land.leets.domain.interview.usecase;

import land.leets.domain.interview.domain.Interview;
import land.leets.domain.interview.presentation.dto.req.InterviewRequest;
import land.leets.domain.interview.type.HasInterview;

import java.util.UUID;

public interface CreateInterview {
    void execute(UUID uid, HasInterview hasInterview);

    Interview execute(InterviewRequest request);
}
