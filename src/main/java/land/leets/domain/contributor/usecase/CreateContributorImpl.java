package land.leets.domain.contributor.usecase;

import land.leets.domain.contributor.domain.Contributor;
import land.leets.domain.contributor.domain.repository.ContributorRepository;
import land.leets.domain.portfolio.domain.Portfolio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateContributorImpl implements CreateContributor {

    private final ContributorRepository contributorRepository;

    @Override
    public void execute(List<Contributor> request, Portfolio portfolio) {
        for (Contributor contributor : request) {
            contributor.setPortfolio(portfolio);
            contributorRepository.save(contributor);
        }
    }
}
