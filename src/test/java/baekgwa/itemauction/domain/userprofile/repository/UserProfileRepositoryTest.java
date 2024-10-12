package baekgwa.itemauction.domain.userprofile.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import baekgwa.itemauction.IntegrationSpringBootTest;
import baekgwa.itemauction.domain.user.entity.User;
import baekgwa.itemauction.domain.userprofile.dto.UserProfileDto;
import baekgwa.itemauction.domain.userprofile.dto.UserProfileDto.MainInfo;
import baekgwa.itemauction.domain.userprofile.dto.UserProfileDto.ProfileInfo;
import baekgwa.itemauction.domain.userprofile.entity.UserGrade;
import baekgwa.itemauction.domain.userprofile.entity.UserProfile;
import baekgwa.itemauction.global.exception.CustomException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class UserProfileRepositoryTest extends IntegrationSpringBootTest {

    @DisplayName("[Success] Unique 한 값인, 이메일, 닉네임, 전화번호로 데이터를 찾아옵니다. 없으면 Optional Null")
    @Test
    void findAllByUniqueValuesCase1() {
        // given

        // when
        Optional<UserProfile> findData = userProfileRepository.findFirstByEmailOrNickNameOrPhone(
                "test1",
                "email@email.com", "01012341234");

        // then
        assertThat(findData).isEmpty();
    }

    @DisplayName("[Success] Unique 한 값중 이메일이 있으면 데이터를 찾아옵니다.")
    @Test
    void findAllByUniqueValuesCase2() {
        // given
        User savedUserData = userRepository.save(User.createNewUser("test1", "1234"));
        String email = "email@email.com";
        userProfileRepository.save(
                UserProfile.createNewUserProfile(
                        savedUserData, "name1", "nickName",
                        email, "01012341234"));

        // when
        Optional<UserProfile> findData =
                userProfileRepository.findFirstByEmailOrNickNameOrPhone(email, "중복방지", "중복방지");

        // then
        assertThat(findData.isPresent()).isTrue();
        assertThat(findData.get().getEmail()).isEqualTo(email);
    }

    @DisplayName("[Success] Unique 한 값중 닉네임이 있으면 데이터를 찾아옵니다.")
    @Test
    void findAllByUniqueValuesCase3() {
        // given
        User savedUserData = userRepository.save(User.createNewUser("test1", "1234"));
        String nickName = "nickName";
        userProfileRepository.save(
                UserProfile.createNewUserProfile(
                        savedUserData, "name1", nickName,
                        "email@email.com", "01012341234"));

        // when
        Optional<UserProfile> findData =
                userProfileRepository.findFirstByEmailOrNickNameOrPhone("중복방지", nickName, "중복방지");

        // then
        assertThat(findData.isPresent()).isTrue();
        assertThat(findData.get().getNickName()).isEqualTo(nickName);
    }

    @DisplayName("[Success] Unique 한 값중 전화번호가 있으면 데이터를 찾아옵니다.")
    @Test
    void findAllByUniqueValuesCase4() {
        // given
        User savedUserData = userRepository.save(User.createNewUser("test1", "1234"));
        String phone = "01012341234";
        userProfileRepository.save(
                UserProfile.createNewUserProfile(
                        savedUserData, "name1", "nickName",
                        "email@email.com", phone));

        // when
        Optional<UserProfile> findData = userProfileRepository.findFirstByEmailOrNickNameOrPhone(
                "중복방지", "중복방지", phone);

        // then
        assertThat(findData.isPresent()).isTrue();
        assertThat(findData.get().getPhone()).isEqualTo(phone);
    }

    @DisplayName("[Success] 마이페이지에서 보여줄 데이터를 회원 아이디를 통해 찾아옵니다.")
    @Test
    void findUserInfoMyPageFormByUserId() {
        // given
        User savedData = userRepository.save(User.createNewUser("loginId", "password"));
        UserProfile savedUserProfile = userProfileRepository.save(
                UserProfile.createNewUserProfile(savedData, "name", "nickName", "email", "phone"));

        // when
        Optional<UserProfileDto.ProfileInfo> findData = userProfileRepository.findUserInfoMyPageFormByUserId(
                savedData.getId());

        // then
        assertThat(findData.isPresent()).isTrue();
        assertThat(findData.get())
                .extracting("nickName", "name", "email", "phone", "userGrade")
                .contains("nickName", "name", "email", "phone", UserGrade.BRONZE);
    }

    @DisplayName("[Success] 찾아올 마이페이지용 데이터가 없으면 빈값을 반환합니다.")
    @Test
    void findUserInfoMyPageFormByUserIdFailCase() {
        // given

        // when
        Optional<UserProfileDto.ProfileInfo> findData = userProfileRepository.findUserInfoMyPageFormByUserId(1L);

        // then
        assertThat(findData.isEmpty()).isTrue();
    }

    @DisplayName("[Success] 메인페이지에서 보여줄 데이터를 회원 아이디를 통해 찾아옵니다.")
    @Test
    void findUserInfoMainFormByUserId() {
        // given
        User savedData = userRepository.save(User.createNewUser("loginId", "password"));
        UserProfile savedUserProfile = userProfileRepository.save(
                UserProfile.createNewUserProfile(savedData, "name", "nickName", "email", "phone"));

        // when
        Optional<UserProfileDto.MainInfo> findData =
                userProfileRepository.findUserInfoMainFormByUserId(savedData.getId());

        // then
        assertThat(findData.isPresent()).isTrue();
        assertThat(findData.get())
                .extracting("nickName", "name")
                .contains("nickName", "name");
    }

    @DisplayName("[Success] 찾아올 메인페이지용 데이터가 없으면 빈값을 반환합니다.")
    @Test
    void findUserInfoMainFormByUserIdFail() {
        // given

        // when
        Optional<UserProfileDto.MainInfo> findData = userProfileRepository.findUserInfoMainFormByUserId(1L);

        // then
        assertThat(findData.isEmpty()).isTrue();
    }
}