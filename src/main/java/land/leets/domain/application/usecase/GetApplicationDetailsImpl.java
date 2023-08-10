package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.exception.ApplicationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetApplicationDetailsImpl implements GetApplicationDetails{

    private final ApplicationRepository applicationRepository;

    @Override
    public Application execute(Long id) {
        return applicationRepository.findById(id).orElseThrow(ApplicationNotFoundException::new);
    }
}
