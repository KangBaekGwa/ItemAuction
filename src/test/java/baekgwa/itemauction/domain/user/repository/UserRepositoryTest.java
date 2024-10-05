package baekgwa.itemauction.domain.user.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import baekgwa.itemauction.IntegrationSpringBootTest;
import baekgwa.itemauction.domain.user.entity.User;
import baekgwa.itemauction.domain.user.entity.UserRole;
import baekgwa.itemauction.domain.user.entity.UserStatus;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class UserRepositoryTest extends IntegrationSpringBootTest {

    @DisplayName("[Success] 로그인 아이디로 회원이 존재 하는지 확인합니다.")
    @Test
    void existsByLoginId() {
        // given
        String loginId = "test1";
        userRepository.save(User.createNewUser(loginId, "1234"));

        // when
        boolean result = userRepository.existsByLoginId(loginId);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("[Fail] 존재하지 않는 로그인 아이디는 검색되지 않습니다.")
    @Test
    void existsByLoginIdFail() {
        // given
        String loginId = "test1";

        // when
        boolean result = userRepository.existsByLoginId(loginId);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("[Success] LoginId로 회원을 찾습니다. Uuid, role, status 는 기본 설정 되어야 합니다.")
    @Test
    void findByLoginId() {
        // given
        String loginId = "test1";
        userRepository.save(User.createNewUser(loginId, "1234"));

        // when
        Optional<User> result = userRepository.findByLoginId(loginId);

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get())
                .extracting("loginId", "role", "status")
                .contains(
                        loginId, UserRole.NONE, UserStatus.ACTIVE
                );
    }

    @DisplayName("[Fail] 회원을 찾는데, 회원 데이터를 못찾으면 Optional Null 이 반환 됩니다.")
    @Test
    void findByLoginIdFail() {
        // given
        String loginId = "test1";

        // when
        Optional<User> result = userRepository.findByLoginId(loginId);

        // then
        assertThat(result.isEmpty()).isTrue();
    }
}