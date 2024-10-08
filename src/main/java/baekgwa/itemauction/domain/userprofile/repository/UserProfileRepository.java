package baekgwa.itemauction.domain.userprofile.repository;

import baekgwa.itemauction.domain.userprofile.entity.UserProfile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Query("select up from UserProfile up where up.email = :email or up.nickName = :nickName or up.phone = :phone")
    Optional<UserProfile> findAllByUniqueValues(
            @Param("nickName") String nickName, @Param("email") String email, @Param("phone") String phone);
}
