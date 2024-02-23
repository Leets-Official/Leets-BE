package land.leets.domain.contributor.usecase;

import land.leets.domain.contributor.domain.Contributor;
import land.leets.domain.portfolio.domain.Portfolio;

import java.util.List;

public interface CreateContributor {
    void execute(List<Contributor> request, Portfolio portfolio) ;
}
