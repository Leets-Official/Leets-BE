package land.leets.domain.user.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.type.SubmitStatus;
import land.leets.domain.auth.AuthDetails;
import land.leets.domain.user.domain.User;
import land.leets.domain.user.domain.repository.UserRepository;
import land.leets.domain.user.exception.UserNotFoundException;
import land.leets.domain.user.presentation.dto.UserDetailsResponse;
import land.leets.domain.user.presentation.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserDetailsImpl implements GetUserDetails {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ApplicationRepository applicationRepository;

    @Override
    public UserDetailsResponse execute(AuthDetails authDetails) {

        String email = authDetails.getUsername();
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        Application application = applicationRepository.findByUser_Uid(user.getUid()).orElse(null);

        return userMapper.mappingUserToDto(user, application == null ? SubmitStatus.NONE : application.getSubmitStatus());
    }
}
