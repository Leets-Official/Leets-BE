package land.leets.domain.interview.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.interview.domain.Interview;
import land.leets.domain.interview.domain.repository.InterviewRepository;
import land.leets.domain.interview.presentation.dto.res.InterviewDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetInterviewDetailsImpl implements GetInterviewDetails {

    private final InterviewRepository interviewRepository;

    @Override
    public InterviewDetailsResponse execute(Application application) {
        Optional<Interview> interview = interviewRepository.findByApplication(application);
        return interview.map(InterviewDetailsResponse::from).orElse(InterviewDetailsResponse.builder()
                .id(null)
                .hasInterview(null)
                .fixedInterviewDate(null)
                .place(null)
                .build());
    }
}
