package baekgwa.itemauction.domain.userprofile.entity;

import static org.assertj.core.api.Assertions.assertThat;

import baekgwa.itemauction.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserProfileTest {

    @DisplayName("[Success] 새로운 사용자 프로파일을 생성합니다. 등급은 브론즈로 바로 부여됩니다.")
    @Test
    void createNewUserProfile() {
        // given
        User newUser = User.createNewUser("test1", "1234");

        // when
        UserProfile newUserProfile = UserProfile.createNewUserProfile(newUser, "UserA", "백과",
                "email@email.com", "01012341234");

        // then
        assertThat(newUserProfile).isNotNull()
                .extracting("name", "nickName", "email", "phone", "grade")
                .contains("UserA", "백과", "email@email.com", "01012341234", UserGrade.BRONZE);
    }

    @DisplayName("[Success] 사용자의 등급을 변경합니다.")
    @Test
    void updateGrade() {
        // given
        User newUser = User.createNewUser("test1", "1234");
        UserProfile newUserProfile = UserProfile.createNewUserProfile(newUser, "UserA", "백과",
                "email@email.com", "01012341234");

        // when
        newUserProfile.updateGrade(UserGrade.DIAMOND);

        // then
        assertThat(newUserProfile.getGrade()).isEqualTo(UserGrade.DIAMOND);
    }
}