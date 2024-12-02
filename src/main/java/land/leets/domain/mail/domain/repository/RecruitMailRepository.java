package land.leets.domain.mail.domain.repository;

import land.leets.domain.mail.domain.RecruitMail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitMailRepository extends JpaRepository<RecruitMail, Long> {
    List<RecruitMail> findAll();

    boolean existsByEmail(String email);
}
