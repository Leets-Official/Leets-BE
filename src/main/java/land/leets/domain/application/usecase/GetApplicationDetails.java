package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.Application;

public interface GetApplicationDetails {
    Application execute(Long id);
}
