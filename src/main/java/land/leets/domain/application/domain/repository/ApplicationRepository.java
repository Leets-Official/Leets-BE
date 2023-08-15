package land.leets.domain.application.domain.repository;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.type.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByUser_Uid(UUID uid);
    List<Application> findAllByPosition(Position position);
}
