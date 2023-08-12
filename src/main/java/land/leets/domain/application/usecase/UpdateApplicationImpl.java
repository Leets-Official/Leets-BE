package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.exception.ApplicationNotFoundException;
import land.leets.domain.application.presentation.dto.ApplicationRequest;
import land.leets.domain.application.presentation.mapper.ApplicationMapper;
import land.leets.domain.auth.AuthDetails;
import land.leets.domain.user.usecase.UpdateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateApplicationImpl implements UpdateApplication {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final UpdateUser updateUser;

    @Override
    @Transactional
    public Application execute(AuthDetails authDetails, ApplicationRequest request) {
        UUID uid = authDetails.getUid();
        updateUser.execute(uid, request);

        Application application = applicationRepository.findByUser_Uid(uid).orElseThrow(ApplicationNotFoundException::new);
        applicationMapper.updateApplicationFromDto(application, request);

        return applicationRepository.save(application);
    }
}
