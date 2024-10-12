package baekgwa.itemauction.domain.userprofile.repository;

import baekgwa.itemauction.domain.userprofile.dto.UserProfileDto;
import baekgwa.itemauction.domain.userprofile.dto.UserProfileDto.ProfileInfo;
import baekgwa.itemauction.domain.userprofile.entity.UserProfile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findFirstByEmailOrNickNameOrPhone(String email, String nickName, String phone);

    @Query("SELECT new baekgwa.itemauction.domain.userprofile.dto.UserProfileDto$ProfileInfo(up.nickName, up.name, up.email, up.phone, up.grade) FROM UserProfile up WHERE up.userId = :userId")
    Optional<ProfileInfo> findUserInfoMyPageFormByUserId(@Param("userId") Long userId);

    @Query("SELECT new baekgwa.itemauction.domain.userprofile.dto.UserProfileDto$MainInfo(up.nickName, up.name) FROM UserProfile up WHERE up.userId = :userId")
    Optional<UserProfileDto.MainInfo> findUserInfoMainFormByUserId(@Param("userId") Long userId);
}
