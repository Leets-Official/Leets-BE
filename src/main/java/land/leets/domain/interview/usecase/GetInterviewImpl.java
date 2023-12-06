package land.leets.domain.interview.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.interview.domain.Interview;
import land.leets.domain.interview.domain.repository.InterviewRepository;
import land.leets.domain.interview.exception.InterviewNotFoundException;
import land.leets.domain.interview.presentation.dto.res.InterviewResponse;
import land.leets.domain.interview.presentation.mapper.InterviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetInterviewImpl implements GetInterview {
    private final InterviewRepository interviewRepository;
    private final InterviewMapper interviewMapper;

    @Override
    public InterviewResponse execute(Application application) {
        Interview interview = interviewRepository.findByApplication(application).orElseThrow(InterviewNotFoundException::new);
        return interviewMapper.mappingToDto(interview);
    }
}
