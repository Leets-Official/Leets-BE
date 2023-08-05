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

    @Query("SELECT u.refreshToken FROM users u WHERE u.uid=:uid")
    String getRefreshTokenById(@Param("uid") UUID uid);

    @Transactional
    @Modifying
    @Query("UPDATE users u SET u.refreshToken=:token WHERE u.uid=:uid")
    void updateRefreshToken(@Param("uid") UUID uid, @Param("token") String token);
}
