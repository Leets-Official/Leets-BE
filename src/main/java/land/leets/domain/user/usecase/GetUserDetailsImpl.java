package land.leets.domain.user.usecase;

import land.leets.domain.auth.AuthDetails;
import land.leets.domain.user.domain.User;
import land.leets.domain.user.domain.repository.UserRepository;
import land.leets.domain.user.exception.UserNotFoundException;
import land.leets.domain.user.presentation.dto.UserDetailsResponse;
import land.leets.domain.user.presentation.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserDetailsImpl implements GetUserDetails {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetailsResponse execute(AuthDetails authDetails) {

        String sub = authDetails.getUsername();
        User user = userRepository.findBySub(sub).orElseThrow(UserNotFoundException::new);
        return userMapper.mappingUserToDto(user);
    }
}
