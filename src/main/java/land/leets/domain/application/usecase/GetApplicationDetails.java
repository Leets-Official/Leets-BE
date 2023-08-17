package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.presentation.dto.ApplicationDetailsResponse;

import java.util.UUID;

public interface GetApplicationDetails {
    ApplicationDetailsResponse execute(Long id);
    Application execute(UUID uid);
}
