package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.exception.ApplicationNotFoundException;
import land.leets.domain.application.presentation.dto.ApplicationDetailsResponse;
import land.leets.domain.application.presentation.mapper.ApplicationMapper;
import land.leets.domain.interview.presentation.dto.res.InterviewDetailsResponse;
import land.leets.domain.interview.usecase.GetInterviewDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetApplicationDetailsImpl implements GetApplicationDetails {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final GetInterviewDetails getInterviewDetails;

    @Override
    public ApplicationDetailsResponse execute(Long id) {
        Application application = applicationRepository.findById(id).orElseThrow(ApplicationNotFoundException::new);
        InterviewDetailsResponse interview = getInterviewDetails.execute(application);
        String phone = application.getUser().getPhone();
        return applicationMapper.mappingDetailsToDto(application, interview, phone);
    }

    @Override
    public Application execute(UUID uid) {
        return applicationRepository.findByUser_Uid(uid).orElseThrow(ApplicationNotFoundException::new);
    }
}
