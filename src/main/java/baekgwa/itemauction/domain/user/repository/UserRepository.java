package baekgwa.itemauction.domain.user.repository;

import baekgwa.itemauction.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select count(u) > 0 from User u where u.loginId = :loginId")
    boolean existsByLoginId(@Param("loginId") String loginId);

    Optional<User> findByLoginId(String loginId);
}
