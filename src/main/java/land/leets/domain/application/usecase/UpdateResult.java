package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.presentation.dto.ResultRequest;

public interface UpdateResult {
    Application execute(Long id, ResultRequest resultRequest);
}
