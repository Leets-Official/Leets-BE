package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.Application;

public interface HasInterview {
    Application execute(String email);
}