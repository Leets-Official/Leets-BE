package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.exception.ApplicationNotFoundException;
import land.leets.domain.user.domain.User;
import land.leets.domain.user.domain.repository.UserRepository;
import land.leets.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static land.leets.domain.application.type.HasInterview.*;

@Service
@RequiredArgsConstructor
public class HasInterviewImpl implements HasInterview {

    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    @Override
    public Application execute(String email, boolean attend) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Application application = applicationRepository.findByUser_Uid(user.getUid()).orElseThrow(ApplicationNotFoundException::new);
        if (attend) {
            application.setHasInterview(CHECK);
        } else {
            application.setHasInterview(UNCHECK);
        }
        return applicationRepository.save(application);
    }
}
