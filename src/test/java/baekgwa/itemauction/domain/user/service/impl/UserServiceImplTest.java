package baekgwa.itemauction.domain.user.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import baekgwa.itemauction.IntegrationSpringBootTest;
import baekgwa.itemauction.domain.user.entity.User;
import baekgwa.itemauction.domain.user.entity.UserRole;
import baekgwa.itemauction.domain.user.entity.UserStatus;
import baekgwa.itemauction.domain.userprofile.entity.UserGrade;
import baekgwa.itemauction.domain.userprofile.entity.UserProfile;
import baekgwa.itemauction.global.exception.CustomErrorCode;
import baekgwa.itemauction.global.exception.CustomException;
import baekgwa.itemauction.web.user.UserForm.NewUser;
import baekgwa.itemauction.web.user.UserResponse.CheckDuplicateLoginId;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class UserServiceImplTest extends IntegrationSpringBootTest {

    @DisplayName("[Success] 새로운 회원을 등록합니다.")
    @Test
    void addNewUser() {
        // given
        String loginId = "test1";
        NewUser newUser = createNewUser(loginId, "1234", "name1", "닉네임1", "email@email.com",
                "01012341234");

        // when
        userService.addNewUser(newUser);

        // then
        Optional<User> findUserData = userRepository.findByLoginId(loginId);
        assertThat(findUserData.isPresent()).isTrue();
        assertThat(findUserData.get())
                .extracting("loginId", "role", "status")
                .contains(loginId, UserRole.NONE, UserStatus.ACTIVE);
        assertThat(findUserData.get())
                .extracting("password")
                .isNotNull()
                .isNotEqualTo("1234");

        Optional<UserProfile> findUserProfileData = userProfileRepository.findById(
                findUserData.get().getId());
        assertThat(findUserProfileData.isPresent()).isTrue();
        assertThat(findUserProfileData.get())
                .extracting("name", "nickName", "email", "phone", "grade")
                .contains("name1", "닉네임1", "email@email.com", "01012341234", UserGrade.BRONZE);
    }

    @DisplayName("[Fail] 중복된 Id로 회원가입은 불가능 합니다.")
    @Test
    void addNewUserDuplicatedLoginId() {
        // given
        String loginId = "test1";
        NewUser newUser = createNewUser(loginId, "1234", "name1", "닉네임1", "email@email.com",
                "01012341234");

        User alreadyUserData = User.createNewUser(loginId, "1234");
        User savedData = userRepository.save(alreadyUserData);
        userProfileRepository.save(
                UserProfile.createNewUserProfile(savedData, "중복방지", "중복방지닉네임",
                        "중복방지@email.com", "01023232323"));

        // when // then
        assertThatThrownBy(() -> userService.addNewUser(newUser))
                .isInstanceOf(CustomException.class)
                .extracting("code")
                .isEqualTo(CustomErrorCode.ADD_USER_ERROR_DUPLICATED_LOGIN_ID);
    }

    @DisplayName("[Fail] 중복된 닉네임으로 회원가입은 불가능 합니다.")
    @Test
    void addNewUserDuplicatedNickName() {
        // given
        String nickName = "닉네임1";
        NewUser newUser = createNewUser("test1", "1234", "name1", nickName, "email@email.com",
                "01012341234");

        User alreadyUserData = User.createNewUser("test2", "1234");
        User savedData = userRepository.save(alreadyUserData);
        UserProfile savedProfileData = userProfileRepository.save(
                UserProfile.createNewUserProfile(
                        savedData, "중복방지", nickName, "중복방지@email.com", "01023232323"));

        // when // then
        assertThatThrownBy(() -> userService.addNewUser(newUser))
                .isInstanceOf(CustomException.class)
                .extracting("code")
                .isEqualTo(CustomErrorCode.ADD_USER_ERROR_DUPLICATED_NICKNAME);
    }

    @DisplayName("[Fail] 중복된 전화번호로 회원가입은 불가능 합니다.")
    @Test
    void addNewUserDuplicatedPhone() {
        // given
        String phone = "01029871276";
        NewUser newUser = createNewUser("test1", "1234", "name1", "닉네임1", "email@email.com",
                phone);

        User alreadyUserData = User.createNewUser("test2", "1234");
        User savedData = userRepository.save(alreadyUserData);
        userProfileRepository.save(
                UserProfile.createNewUserProfile(savedData, "중복방지", "닉네임1", "중복방지@email.com",
                        phone));

        // when // then
        assertThatThrownBy(() -> userService.addNewUser(newUser))
                .isInstanceOf(CustomException.class)
                .extracting("code")
                .isEqualTo(CustomErrorCode.ADD_USER_ERROR_DUPLICATED_PHONE);
    }

    @DisplayName("[Fail] 중복된 이메일로 회원가입은 불가능 합니다.")
    @Test
    void addNewUserDuplicatedEmail() {
        // given
        String email = "email@email.com";
        NewUser newUser = createNewUser("test1", "1234", "name1", "닉네임1", email,
                "01029871276");

        User alreadyUserData = User.createNewUser("test2", "1234");
        User savedData = userRepository.save(alreadyUserData);
        userProfileRepository.save(
                UserProfile.createNewUserProfile(savedData, "중복방지", "닉네임1", email, "01029871276"));

        // when // then
        assertThatThrownBy(() -> userService.addNewUser(newUser))
                .isInstanceOf(CustomException.class)
                .extracting("code")
                .isEqualTo(CustomErrorCode.ADD_USER_ERROR_DUPLICATED_EMAIL);
    }

    @DisplayName("[Success] 로그인 아이디는 중복확인을 합니다.")
    @Test
    void checkDuplicateLoginId() {
        // given
        String name = "name1";
        String nickName = "nickName1";
        User newUser = User.createNewUser("test1", "1234");
        User savedUserData = userRepository.save(newUser);
        UserProfile newUserProfile = UserProfile.createNewUserProfile(savedUserData, name, nickName,
                "email@email.com", "01011112222");
        userProfileRepository.save(newUserProfile);

        // when
        CheckDuplicateLoginId result = userService.checkDuplicateLoginId(
                savedUserData.getLoginId());

        // then
        assertThat(result).isNotNull()
                .extracting("duplicate")
                .isEqualTo(Boolean.TRUE);
    }

    @DisplayName("[Success] 로그인 아이디의 중복을 확인 합니다. Case2")
    @Test
    void checkDuplicateLoginIdCase2() {
        // given

        // when
        CheckDuplicateLoginId result = userService.checkDuplicateLoginId("loginId1");

        // then
        assertThat(result).isNotNull()
                .extracting("duplicate")
                .isEqualTo(Boolean.FALSE);
    }

    private NewUser createNewUser(String loginId, String password, String name, String nickName,
            String email, String phone) {
        NewUser newUser = new NewUser();

        newUser.setLoginId(loginId);
        newUser.setPassword(password);
        newUser.setName(name);
        newUser.setNickName(nickName);
        newUser.setEmail(email);
        newUser.setPhone(phone);

        return newUser;
    }
}