package baekgwa.itemauction.domain.userprofile.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import baekgwa.itemauction.IntegrationSpringBootTest;
import baekgwa.itemauction.domain.user.dto.UserProfileDataDto;
import baekgwa.itemauction.domain.user.entity.User;
import baekgwa.itemauction.domain.userprofile.entity.UserProfile;
import baekgwa.itemauction.global.exception.CustomErrorCode;
import baekgwa.itemauction.global.exception.CustomException;
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
        UserProfileDataDto dto = userProfileService.findUserDataMainForm(savedData.getId());

        // then
        assertThat(dto).isNotNull()
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
}