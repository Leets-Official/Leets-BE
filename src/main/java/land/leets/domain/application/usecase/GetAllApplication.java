package land.leets.domain.application.usecase;

import land.leets.domain.application.presentation.dto.ApplicationResponse;

import java.util.List;

public interface GetAllApplication {
    List<ApplicationResponse> execute(String position);
}
