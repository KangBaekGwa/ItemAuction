package baekgwa.itemauction.domain.user.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @DisplayName("[Success] 새로운 사용자를 생성합니다. 사용자는 기본적으로 권한은 없고, Uuid는 자동으로 생성 됩니다.")
    @Test
    void createNewUser() {
        // given
        String loginId = "test1";
        String password = "1234";

        // when
        User newUser = User.createNewUser(loginId, password);

        // then
        assertThat(newUser)
                .extracting("loginId", "password", "role", "status")
                .contains(loginId, password, UserRole.NONE, UserStatus.ACTIVE);
        assertThat(newUser.getUuid()).isNotNull();
    }

    @DisplayName("[Success] 새로운 관리자를 생성합니다.")
    @Test
    void createNewAdmin() {
        // given
        String loginId = "test1";
        String password = "1234";

        // when
        User newUser = User.createNewAdmin(loginId, password);

        // then
        assertThat(newUser)
                .extracting("loginId", "password", "role", "status")
                .contains(loginId, password, UserRole.ADMIN, UserStatus.ACTIVE);
        assertThat(newUser.getUuid()).isNotNull();
    }

    @DisplayName("[Success] 회원의 Role 값을 변경합니다.")
    @Test
    void updateRole() {
        // given
        String loginId = "test1";
        String password = "1234";
        User newUser = User.createNewUser(loginId, password);

        // when
        newUser.updateRole(UserRole.BUYER);

        // then
        assertThat(newUser)
                .extracting("loginId", "password", "role", "status")
                .contains(loginId, password, UserRole.BUYER, UserStatus.ACTIVE);
        assertThat(newUser.getUuid()).isNotNull();
    }

    @DisplayName("[Success] 회원의 Role 값을 변경합니다.")
    @Test
    void updateStatus() {
        // given
        String loginId = "test1";
        String password = "1234";
        User newUser = User.createNewUser(loginId, password);

        // when
        newUser.updateStatus(UserStatus.BLOCKED);

        // then
        assertThat(newUser)
                .extracting("loginId", "password", "role", "status")
                .contains(loginId, password, UserRole.NONE, UserStatus.BLOCKED);
        assertThat(newUser.getUuid()).isNotNull();
    }
}