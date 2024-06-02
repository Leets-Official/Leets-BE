package land.leets.domain.contributor.domain.repository;

import land.leets.domain.contributor.domain.Contributor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributorRepository extends JpaRepository<Contributor, Long> {
}
