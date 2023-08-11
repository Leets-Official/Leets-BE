package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.Application;

import java.util.UUID;

public interface GetApplicationDetails {
    Application execute(Long id);
    Application execute(UUID uid);
}
