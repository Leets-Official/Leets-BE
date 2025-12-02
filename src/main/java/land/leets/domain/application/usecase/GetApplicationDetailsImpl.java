package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.exception.ApplicationNotFoundException;
import land.leets.domain.application.presentation.dto.ApplicationDetailsResponse;
import land.leets.domain.interview.presentation.dto.res.InterviewDetailsResponse;
import land.leets.domain.interview.usecase.GetInterviewDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetApplicationDetailsImpl implements GetApplicationDetails {

    private final ApplicationRepository applicationRepository;
    private final GetInterviewDetails getInterviewDetails;

    @Override
    public ApplicationDetailsResponse execute(Long id) {
        Application application = applicationRepository.findById(id).orElseThrow(ApplicationNotFoundException::new);
        return getDetails(application);
    }

    @Override
    public Application execute(UUID uid) {
        return applicationRepository.findByUser_Uid(uid).orElseThrow(ApplicationNotFoundException::new);
    }

    private ApplicationDetailsResponse getDetails(Application application) {
        InterviewDetailsResponse interview = getInterviewDetails.execute(application);
        String phone = application.getUser().getPhone();
        return ApplicationDetailsResponse.of(application, interview, phone);
    }
}
