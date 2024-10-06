package baekgwa.itemauction;

import baekgwa.itemauction.domain.user.entity.User;
import baekgwa.itemauction.domain.user.entity.UserRole;
import baekgwa.itemauction.domain.user.repository.UserRepository;
import baekgwa.itemauction.domain.userprofile.entity.UserProfile;
import baekgwa.itemauction.domain.userprofile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class TestUserInit {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 실행시 사용할 Admin 용 계정 생성
     */
    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        User newUser1 = User.createNewAdmin("admin1", bCryptPasswordEncoder.encode("1234"));
        User savedData1 = userRepository.save(newUser1);
        UserProfile newUserProfile1 = UserProfile.createNewUserProfile(savedData1, "테스트어드민A", "백과(어드민)",
                "admintest@test.com", "01000000001");
        userProfileRepository.save(newUserProfile1);

        User newUser2 = User.createNewAdmin("trader1", bCryptPasswordEncoder.encode("1234"));
        newUser2.updateRole(UserRole.TRADER);
        User savedData2 = userRepository.save(newUser2);
        UserProfile newUserProfile2 = UserProfile.createNewUserProfile(savedData2, "테스트트레이더A", "백과(트레이더)",
                "tradertest@test.com", "01000000002");
        userProfileRepository.save(newUserProfile2);

        User newUser3 = User.createNewAdmin("buyer1", bCryptPasswordEncoder.encode("1234"));
        newUser3.updateRole(UserRole.BUYER);
        User savedData3 = userRepository.save(newUser3);
        UserProfile newUserProfile3 = UserProfile.createNewUserProfile(savedData3, "테스트구매자A", "백과(구매자)",
                "buyertest@test.com", "01000000003");
        userProfileRepository.save(newUserProfile3);

        User newUser4 = User.createNewUser("none1", bCryptPasswordEncoder.encode("1234"));
        User savedData4 = userRepository.save(newUser4);
        UserProfile newUserProfile4 = UserProfile.createNewUserProfile(savedData4, "테스트미인증A", "백과(미인증)",
                "nonetest@test.com", "01000000004");
        userProfileRepository.save(newUserProfile4);
    }
}