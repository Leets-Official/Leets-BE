package land.leets.domain.admin.domain.repository;

import land.leets.domain.admin.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
