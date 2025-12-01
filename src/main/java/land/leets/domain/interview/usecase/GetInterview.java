package land.leets.domain.interview.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.auth.AuthDetails;
import land.leets.domain.interview.presentation.dto.res.InterviewResponse;

public interface GetInterview {
    InterviewResponse execute(Application application);
}
