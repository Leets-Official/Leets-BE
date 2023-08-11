package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.exception.ApplicationNotFoundException;
import land.leets.domain.application.presentation.dto.StatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateResultImpl implements UpdateResult {

    private final ApplicationRepository applicationRepository;

    @Override
    public Application execute(Long id, StatusRequest request) {
        Application application = applicationRepository.findById(id).orElseThrow(ApplicationNotFoundException::new);
        application.setApplicationStatus(request.getApplicationStatus());

        return applicationRepository.save(application);
    }
}
