package land.leets.domain.user.usecase;

import land.leets.domain.application.presentation.dto.ApplicationRequest;
import land.leets.domain.user.domain.User;
import land.leets.domain.user.domain.repository.UserRepository;
import land.leets.domain.user.exception.UserNotFoundException;
import land.leets.domain.user.presentation.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserImpl implements UpdateUser {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User execute(UUID uid, ApplicationRequest request) {

        User user = userRepository.findByUid(uid).orElseThrow(UserNotFoundException::new);
        userMapper.updateUserFromDto(user, request);

        return userRepository.save(user);
    }
}
