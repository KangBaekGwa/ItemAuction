package baekgwa.itemauction.domain.userprofile.repository;

import static org.assertj.core.api.Assertions.assertThat;

import baekgwa.itemauction.IntegrationSpringBootTest;
import baekgwa.itemauction.domain.user.entity.User;
import baekgwa.itemauction.domain.userprofile.entity.UserProfile;
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
        Optional<UserProfile> findData = userProfileRepository.findAllByUniqueValues("test1",
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
        Optional<UserProfile> findData = userProfileRepository.findAllByUniqueValues(
                "중복방지", email, "중복방지");

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
        Optional<UserProfile> findData = userProfileRepository.findAllByUniqueValues(
                nickName, "중복방지", "중복방지");

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
        Optional<UserProfile> findData = userProfileRepository.findAllByUniqueValues(
                "중복방지", "중복방지", phone);

        // then
        assertThat(findData.isPresent()).isTrue();
        assertThat(findData.get().getPhone()).isEqualTo(phone);
    }
}