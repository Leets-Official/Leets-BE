package land.leets.domain.interview.usecase;

import land.leets.domain.application.domain.Application;

public interface HasInterview {
    Application execute(String email, boolean attend);
}