package land.leets.domain.user.domain.repository;

import land.leets.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);

    @Query("SELECT u.refreshToken FROM users u WHERE u.id=:id")
    String getRefreshTokenById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE users u SET u.refreshToken=:token WHERE u.id=:id")
    void updateRefreshToken(@Param("id") Long id, @Param("token") String token);
}
