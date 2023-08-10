package land.leets.domain.application.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetApplicationImpl implements GetApplication{
    private final ApplicationRepository applicationRepository;

    @Override
    public List<Application> execute(String position) {
        if (position == null) {
            return applicationRepository.findAll();
        }
        return applicationRepository.findAllByPosition(position);
    }
}
