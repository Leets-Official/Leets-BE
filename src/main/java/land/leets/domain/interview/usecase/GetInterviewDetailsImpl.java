package land.leets.domain.interview.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.interview.domain.Interview;
import land.leets.domain.interview.domain.repository.InterviewRepository;
import land.leets.domain.interview.exception.InterviewNotFoundException;
import land.leets.domain.interview.presentation.dto.res.InterviewDetailsResponse;
import land.leets.domain.interview.presentation.mapper.InterviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetInterviewDetailsImpl implements GetInterviewDetails {
    private final InterviewRepository interviewRepository;
    private final InterviewMapper interviewMapper;

    @Override
    public InterviewDetailsResponse execute(Application application) {
        Interview interview = interviewRepository.findByApplication(application).orElseThrow(InterviewNotFoundException::new);
        return interviewMapper.mappingDetailsToDto(interview);
    }
}
