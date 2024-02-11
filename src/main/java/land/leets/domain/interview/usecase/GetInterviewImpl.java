package land.leets.domain.interview.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.interview.domain.Interview;
import land.leets.domain.interview.domain.repository.InterviewRepository;
import land.leets.domain.interview.presentation.dto.res.InterviewResponse;
import land.leets.domain.interview.presentation.mapper.InterviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetInterviewImpl implements GetInterview {
    private final InterviewRepository interviewRepository;
    private final InterviewMapper interviewMapper;

    @Override
    public InterviewResponse execute(Application application) {
        Optional<Interview> interview = interviewRepository.findByApplication(application);
        if (interview.isPresent()) {
            return interviewMapper.mappingToDto(interview.get());
        }
        return InterviewResponse.builder().build();
    }
}
