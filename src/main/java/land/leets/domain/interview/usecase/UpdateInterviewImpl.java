package land.leets.domain.interview.usecase;

import land.leets.domain.interview.domain.Interview;
import land.leets.domain.interview.domain.repository.InterviewRepository;
import land.leets.domain.interview.exception.InterviewNotFoundException;
import land.leets.domain.interview.presentation.dto.req.FixedInterviewRequest;
import land.leets.domain.interview.presentation.dto.req.InterviewAttendanceRequest;
import land.leets.domain.interview.presentation.mapper.InterviewMapper;
import land.leets.domain.user.domain.User;
import land.leets.domain.user.domain.repository.UserRepository;
import land.leets.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateInterviewImpl implements UpdateInterview {

    private final UserRepository userRepository;
    private final InterviewRepository interviewRepository;
    private final InterviewMapper interviewMapper;

    @Transactional
    @Override
    public Interview byUser(InterviewAttendanceRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(UserNotFoundException::new);
        Interview interview = interviewRepository.findByApplication_User(user).orElseThrow(InterviewNotFoundException::new);
        interviewMapper.updateInterviewFromDto(interview, request);
        return interviewRepository.save(interview);
    }

    @Override
    public Interview byAdmin(Long id, FixedInterviewRequest request) {
        Interview interview = interviewRepository.findById(id).orElseThrow(InterviewNotFoundException::new);
        interviewMapper.updateInterviewFromDto(interview, request);
        return interviewRepository.save(interview);
    }
}
