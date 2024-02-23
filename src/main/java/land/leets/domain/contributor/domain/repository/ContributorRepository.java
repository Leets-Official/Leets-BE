package land.leets.domain.contributor.domain.repository;

import land.leets.domain.comment.domain.Comment;
import land.leets.domain.contributor.domain.Contributor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ContributorRepository extends JpaRepository<Contributor, Long> {
}