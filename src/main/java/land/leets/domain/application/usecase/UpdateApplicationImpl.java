package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.exception.ApplicationNotFoundException;
import land.leets.domain.application.presentation.dto.ApplicationRequest;
import land.leets.domain.application.presentation.mapper.ApplicationMapper;
import land.leets.domain.auth.AuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateApplicationImpl implements UpdateApplication {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    @Override
    public Application execute(AuthDetails authDetails, ApplicationRequest request) {
        UUID uid = authDetails.getUid();

        Application application = applicationRepository.findByUser_Uid(uid).orElseThrow(ApplicationNotFoundException::new);
        applicationMapper.updateApplicationFromDto(application, request);
        return applicationRepository.save(application);
    }
}
