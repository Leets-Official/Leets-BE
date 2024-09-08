package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.presentation.dto.ApplicationResponse;
import land.leets.domain.application.presentation.mapper.ApplicationMapper;
import land.leets.domain.application.type.Position;
import land.leets.domain.application.type.SubmitStatus;
import land.leets.domain.interview.presentation.dto.res.InterviewResponse;
import land.leets.domain.interview.usecase.GetInterview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllApplicationImpl implements GetAllApplication {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final GetInterview getInterview;

    @Override
    public List<ApplicationResponse> execute() {
        return applicationRepository.findAllByOrderByAppliedAtDesc().stream()
                .map(application -> {
                    InterviewResponse interview = getInterview.execute(application);
                    String phone = application.getUser().getPhone();
                    return applicationMapper.mappingToDto(application, interview, phone);
                })
                .toList();
    }

    @Override
    public List<ApplicationResponse> execute(String position, String status) {

        if (position != null) {
            Position filter = Position.valueOf(position.toUpperCase());
            return applicationRepository.findAllByPositionOrderByAppliedAtDesc(filter).stream()
                    .map(application -> {
                        InterviewResponse interview = getInterview.execute(application);
                        String phone = application.getUser().getPhone();
                        return applicationMapper.mappingToDto(application, interview, phone);
                    })
                    .toList();
        }

        SubmitStatus filter = SubmitStatus.valueOf(status.toUpperCase());
        return applicationRepository.findAllBySubmitStatusOrderByAppliedAtDesc(filter).stream()
                .map(application -> {
                    InterviewResponse interview = getInterview.execute(application);
                    String phone = application.getUser().getPhone();
                    return applicationMapper.mappingToDto(application, interview, phone);
                })
                .toList();
    }
}
