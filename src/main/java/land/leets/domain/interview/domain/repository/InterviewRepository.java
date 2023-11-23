package land.leets.domain.interview.domain.repository;

import land.leets.domain.application.domain.Application;
import land.leets.domain.interview.domain.Interview;
import land.leets.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    Optional<Interview> findByApplication_User(User user);
    Optional<Interview> findByApplication(Application application);
}
