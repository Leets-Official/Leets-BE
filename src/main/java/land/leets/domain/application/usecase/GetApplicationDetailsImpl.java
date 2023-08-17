package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.exception.ApplicationNotFoundException;
import land.leets.domain.application.presentation.dto.ApplicationDetailsResponse;
import land.leets.domain.application.presentation.mapper.ApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetApplicationDetailsImpl implements GetApplicationDetails{

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    @Override
    public ApplicationDetailsResponse execute(Long id) {
        Application application = applicationRepository.findById(id).orElseThrow(ApplicationNotFoundException::new);
        String phone = application.getUser().getPhone();
        return applicationMapper.mappingApplicationToDto(application, phone);
    }

    @Override
    public Application execute(UUID uid) {
        return applicationRepository.findByUser_Uid(uid).orElseThrow(ApplicationNotFoundException::new);
    }
}
