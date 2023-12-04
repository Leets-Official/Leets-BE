package land.leets.domain.mail.usecase;

import land.leets.domain.interview.type.HasInterview;

import java.util.UUID;

public interface CreateInterviewRequest {
    void execute(UUID uid, HasInterview hasInterview);
}
