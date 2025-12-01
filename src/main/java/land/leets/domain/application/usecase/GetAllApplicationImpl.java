package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.presentation.dto.ApplicationResponse;
import land.leets.domain.application.type.ApplicationStatus;
import land.leets.domain.application.type.Position;
import land.leets.domain.interview.presentation.dto.res.InterviewResponse;
import land.leets.domain.interview.usecase.GetInterview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllApplicationImpl implements GetAllApplication {

    private final ApplicationRepository applicationRepository;
    private final GetInterview getInterview;

    @Override
    public List<ApplicationResponse> execute() {
        return mapApplications(applicationRepository.findAllByOrderByAppliedAtDesc());
    }

    @Override
    public List<ApplicationResponse> execute(String position, String status) {
        Position pos = position != null ? Position.valueOf(position.toUpperCase()) : null;
        ApplicationStatus stat = status != null ? ApplicationStatus.valueOf(status.toUpperCase()) : null;

        if (pos != null && stat != null) {
            return mapApplications(applicationRepository.findAllByPositionAndApplicationStatusOrderByAppliedAtDesc(pos, stat));
        }

        if (pos != null) {
            return mapApplications(applicationRepository.findAllByPositionOrderByAppliedAtDesc(pos));
        }

        return mapApplications(applicationRepository.findAllByApplicationStatusOrderByAppliedAtDesc(stat));
    }

    private List<ApplicationResponse> mapApplications(List<Application> applications) {
        return applications.stream()
                .map(application -> {
                    InterviewResponse interview = getInterview.execute(application);
                    String phone = application.getUser().getPhone();
                    return ApplicationResponse.of(application, interview, phone);
                })
                .toList();
    }
}
