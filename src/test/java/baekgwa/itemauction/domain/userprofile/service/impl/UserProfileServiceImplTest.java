package baekgwa.itemauction.domain.userprofile.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import baekgwa.itemauction.IntegrationSpringBootTest;
import baekgwa.itemauction.domain.user.entity.User;
import baekgwa.itemauction.domain.userprofile.entity.UserGrade;
import baekgwa.itemauction.domain.userprofile.entity.UserProfile;
import baekgwa.itemauction.global.exception.CustomErrorCode;
import baekgwa.itemauction.global.exception.CustomException;
import baekgwa.itemauction.web.main.MainForm;
import baekgwa.itemauction.web.mypage.MyPageForm;
import baekgwa.itemauction.web.mypage.MyPageForm.ProfileInfo;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class UserProfileServiceImplTest extends IntegrationSpringBootTest {

    @DisplayName("[Success] 회원 id로, 회원의 프로파일 정보 중, Main 페이지 에서 필요한 정보를 찾습니다.")
    @Test
    void findUserDataMainForm() {
        // given
        User savedData = userRepository.save(User.createNewUser("test1", "1234"));
        UserProfile savedProfileData = userProfileRepository.save(
                UserProfile.createNewUserProfile(savedData, "test", "닉네임", "email@email.com",
                        "01012341234"));

        // when
        MainForm.UserInfo findData = userProfileService.findUserDataMainForm(savedData.getId());

        // then
        assertThat(findData)
                .extracting("name", "nickName")
                .contains("test", "닉네임");
    }

    @DisplayName("[Fail] 회원 id로, 이름과 닉네임을 찾기 실패하면 오류가 발생하면서 메세지가 발행됩니다.")
    @Test
    void findUserDataMainFormNotFoundCase() {
        // given

        // when // then
        assertThatThrownBy(() -> userProfileService.findUserDataMainForm(1L))
                .isInstanceOf(CustomException.class)
                .extracting("code")
                .isEqualTo(CustomErrorCode.FIND_USER_PROFILE_ERROR_NOT_FIND);
    }

    @DisplayName("[Success] 기존 회원에 입력받은 새로운 회원 프로파일 정보로 업데이트 합니다.")
    @Test
    void updateUserProfile() {
        // given
        User savedData = userRepository.save(User.createNewUser("test1", "1234"));
        UserProfile savedProfileData = userProfileRepository.save(
                UserProfile.createNewUserProfile(savedData, "test", "닉네임", "email@email.com",
                        "01012341234"));

        MyPageForm.ChangeProfile changeProfile =
                MyPageForm.ChangeProfile
                        .builder()
                        .nickName("changeNickName")
                        .email("changeEmail@email.com")
                        .name("changeName")
                        .phone("01099999999")
                        .build();

        // when
        userProfileService.updateUserProfile(savedData.getId(), changeProfile);
        Optional<UserProfile> findData = userProfileRepository.findById(savedData.getId());

        // then
        assertThat(findData.isPresent()).isTrue();
        assertThat(findData.get())
                .extracting("userId", "name", "nickName", "email", "phone", "grade")
                .contains(savedData.getId(), "changeName", "changeNickName",
                        "changeEmail@email.com", "01099999999", UserGrade.BRONZE);
    }

    @DisplayName("[Fail] 회원의 Profile 정보를 변경하는데, 찾을 수 없다면 오류메세지가 발행됩니다.")
    @Test
    void updateUserProfileNotFoundCase() {
        // given
        MyPageForm.ChangeProfile changeProfile =
                MyPageForm.ChangeProfile
                        .builder()
                        .nickName("changeNickName")
                        .email("changeEmail@email.com")
                        .name("changeName")
                        .phone("01099999999")
                        .build();

        // when // then
        assertThatThrownBy(() -> userProfileService.updateUserProfile(1L, changeProfile))
                .isInstanceOf(CustomException.class)
                .extracting("code")
                .isEqualTo(CustomErrorCode.FIND_USER_PROFILE_ERROR_NOT_FIND);
    }

    @DisplayName("[Success] 로그인된 회원 아이디로, 메인페이지에서 볼 데이터를 찾아옵니다.")
    @Test
    void findUserDataMyPageForm() {
        // given
        User savedData = userRepository.save(User.createNewUser("test1", "1234"));
        UserProfile savedProfileData = userProfileRepository.save(
                UserProfile.createNewUserProfile(savedData, "test", "닉네임", "email@email.com",
                        "01012341234"));

        // when
        ProfileInfo userDataMyPageForm = userProfileService.findUserDataMyPageForm(
                savedData.getId());

        // then
        assertThat(userDataMyPageForm)
                .extracting("nickName", "name", "email", "phone", "userGrade")
                .contains("닉네임", "test", "email@email.com", "01012341234", UserGrade.BRONZE.getText());
    }

    @DisplayName("[Fail] 메인페이지에서 볼 데이터를 찾아오는데, 회원 id가 매칭되는게 없으면 오류메세지가 발행됩니다.")
    @Test
    void findUserDataMyPageFormFail() {
        // given

        // when

        // then
        assertThatThrownBy(() -> userProfileService.findUserDataMyPageForm(1L))
                .isInstanceOf(CustomException.class)
                .extracting("code")
                .isEqualTo(CustomErrorCode.FIND_USER_PROFILE_ERROR_NOT_FIND);
    }
}