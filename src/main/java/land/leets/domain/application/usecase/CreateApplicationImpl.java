package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.exception.ApplicationAlreadyExistsException;
import land.leets.domain.application.presentation.dto.ApplicationRequest;
import land.leets.domain.auth.AuthDetails;
import land.leets.domain.user.domain.User;
import land.leets.domain.user.domain.repository.UserRepository;
import land.leets.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static land.leets.domain.application.type.SubmitStatus.SUBMIT;

@Service
@RequiredArgsConstructor
public class CreateApplicationImpl implements CreateApplication {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    @Override
    public Application execute(AuthDetails authDetails, ApplicationRequest request) {
        User user = userRepository.findById(authDetails.getUid()).orElseThrow(UserNotFoundException::new);

        if (applicationRepository.findByUser_Uid(user.getUid()).isPresent()) {
            throw new ApplicationAlreadyExistsException();
        }

        user.updateUserInfo(request.getSid(), request.getPhone());
        userRepository.save(user);

        Application application = Application.builder()
                .user(user)
                .name(request.getName())
                .major(request.getMajor())
                .grade(request.getGrade())
                .project(request.getProject())
                .algorithm(request.getAlgorithm())
                .portfolio(request.getPortfolio())
                .position(request.getPosition())
                .career(request.getCareer())
                .interviewDay(request.getInterviewDay())
                .interviewTime(request.getInterviewTime())
                .motive(request.getMotive())
                .expectation(request.getExpectation())
                .capability(request.getCapability())
                .conflict(request.getConflict())
                .passion(request.getPassion())
                .submitStatus(request.getSubmitStatus())
                .appliedAt(request.getSubmitStatus() == SUBMIT ? LocalDateTime.now() : null)
                .build();
        return applicationRepository.save(application);
    }
}
