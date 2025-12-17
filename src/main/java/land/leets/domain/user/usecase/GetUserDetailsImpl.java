package land.leets.domain.user.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.type.SubmitStatus;
import land.leets.domain.auth.AuthDetails;
import land.leets.domain.user.domain.User;
import land.leets.domain.user.domain.repository.UserRepository;
import land.leets.domain.user.exception.UserNotFoundException;
import land.leets.domain.user.presentation.dto.UserDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserDetailsImpl implements GetUserDetails {

    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public UserDetailsResponse execute(AuthDetails authDetails) {
        UUID uid = authDetails.getUid();
        User user = userRepository.findById(uid).orElseThrow(UserNotFoundException::new);
        Application application = applicationRepository.findByUser_Uid(uid);

        return UserDetailsResponse.of(user, application == null ? SubmitStatus.NONE : application.getSubmitStatus());
    }
}
