package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.presentation.dto.ApplicationResponse;
import land.leets.domain.application.presentation.mapper.ApplicationMapper;
import land.leets.domain.application.type.Position;
import land.leets.domain.application.type.SubmitStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllApplicationImpl implements GetAllApplication {
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    @Override
    public List<ApplicationResponse> execute() {
        return applicationRepository.findAll().stream()
                .map(applicationMapper::mappingApplicationToDto).toList();
    }

    @Override
    public List<ApplicationResponse> execute(String position, String status) {

        if (position != null) {
            Position filter = Position.valueOf(position.toUpperCase());
            return applicationRepository.findAllByPosition(filter).stream()
                    .map(applicationMapper::mappingApplicationToDto).toList();
        }

        SubmitStatus filter = SubmitStatus.valueOf(status.toUpperCase());
        return applicationRepository.findAllBySubmitStatus(filter).stream()
                .map(applicationMapper::mappingApplicationToDto).toList();

    }
}
