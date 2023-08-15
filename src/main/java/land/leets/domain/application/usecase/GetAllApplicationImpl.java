package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.presentation.dto.ApplicationResponse;
import land.leets.domain.application.presentation.mapper.ApplicationMapper;
import land.leets.domain.application.type.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllApplicationImpl implements GetAllApplication {
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    @Override
    public List<ApplicationResponse> execute(String position) {

        if (position == null) {
            return applicationRepository.findAll().stream()
                    .map(applicationMapper::mappingApplicationToDto).toList();
        }

        Position filter = Position.valueOf(position.toUpperCase());

        return applicationRepository.findAllByPosition(filter).stream()
                .map(applicationMapper::mappingApplicationToDto).toList();

    }
}
