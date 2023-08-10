package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.presentation.dto.ApplicationRequest;
import land.leets.domain.auth.AuthDetails;
import land.leets.domain.user.domain.User;
import land.leets.domain.user.domain.repository.UserRepository;
import land.leets.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateApplicationImpl implements CreateApplication {
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    @Override
    public Application execute(AuthDetails authDetails, ApplicationRequest request) {
        User user = userRepository.findById(authDetails.getUid()).orElseThrow(UserNotFoundException::new);

        Application application = Application.builder()
                .user(user)
                .name(request.getName())
                .grade(request.getGrade())
                .major(request.getMajor())
                .gpa(request.getGpa())
                .position(request.getPosition())
                .algorithm(request.getAlgorithm())
                .portfolio(request.getPortfolio())
                .interview(request.getInterview())
                .level(request.getLevel())
                .pros(request.getPros())
                .completion(request.getCompletion())
                .enhancement(request.getEnhancement())
                .goal(request.getGoal())
                .build();

        return applicationRepository.save(application);
    }
}
